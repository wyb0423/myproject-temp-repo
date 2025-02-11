package com.myproject.jobapp.controller;

import com.myproject.jobapp.entity.JobPostActivity;
import com.myproject.jobapp.entity.JobSeekerProfile;
import com.myproject.jobapp.entity.JobSeekerSave;
import com.myproject.jobapp.entity.Users;
import com.myproject.jobapp.service.JobPostActivityService;
import com.myproject.jobapp.service.JobSeekerProfileService;
import com.myproject.jobapp.service.JobSeekerSaveService;
import com.myproject.jobapp.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerSaveController {
    private final UserService userService;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final JobPostActivityService jobPostActivityService;
    private final JobSeekerSaveService jobSeekerSaveService;

    public JobSeekerSaveController(UserService userService, JobSeekerProfileService jobSeekerProfileService, JobPostActivityService jobPostActivityService, JobSeekerSaveService jobSeekerSaveService) {
        this.userService = userService;
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.jobPostActivityService = jobPostActivityService;
        this.jobSeekerSaveService = jobSeekerSaveService;
    }

    @PostMapping("job-details/save/{id}")
    public String saveJob(@PathVariable("id")Integer id, JobSeekerSave jobSeekerSave, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)){
            String currentUserName = auth.getName();
            Users user = userService.findByEmail(currentUserName).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.getJobSeekerProfileById(user.getId());
            JobPostActivity jobPostActivity = jobPostActivityService.findById(Long.valueOf(id));
            if (jobSeekerProfile.isPresent() && jobPostActivity != null) {
                jobSeekerSave = new JobSeekerSave();
                jobSeekerSave.setJob(jobPostActivity);
                jobSeekerSave.setUserId(jobSeekerProfile.get());
            } else {
                throw new RuntimeException("user not found");
            }
            jobSeekerSaveService.save(jobSeekerSave);
        }
        return "redirect:/dashboard/";
     }

     @GetMapping("saved-jobs/")
    public String showSavedJobs(Model model) {
        List<JobPostActivity> jobPostActivityList = new ArrayList<>();
        //get current seeker
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.getCurrentJobSeekerProfile();
        //consult the jobs this seeker saved
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidateJobs(jobSeekerProfile);
        //add his saved jobs to display
        for (JobSeekerSave jobSeekerSave : jobSeekerSaveList) {
            jobPostActivityList.add(jobSeekerSave.getJob());
        }
        model.addAttribute("jobPost", jobPostActivityList);
        model.addAttribute("user", jobSeekerProfile);

        return "saved-jobs";
    }
}

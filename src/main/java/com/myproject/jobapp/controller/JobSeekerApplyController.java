package com.myproject.jobapp.controller;

import com.myproject.jobapp.entity.*;
import com.myproject.jobapp.service.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerApplyController {

    private final JobPostActivityService jobPostActivityService;
    private final UserService userService;
    private final JobSeekerApplyService jobSeekerApplyService;
    private final JobSeekerSaveService jobSeekerSaveService;
    private final RecruiterProfileService recruiterProfileService;
    private final JobSeekerProfileService jobSeekerProfileService;

    public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UserService userService, JobSeekerApplyService jobSeekerApplyService, JobSeekerSaveService jobSeekerSaveService, RecruiterProfileService recruiterProfileService, JobSeekerProfileService jobSeekerProfileService) {
        this.jobPostActivityService = jobPostActivityService;
        this.userService = userService;
        this.jobSeekerApplyService = jobSeekerApplyService;
        this.jobSeekerSaveService = jobSeekerSaveService;
        this.recruiterProfileService = recruiterProfileService;
        this.jobSeekerProfileService = jobSeekerProfileService;
    }

    @GetMapping("job-details-apply/{id}")
    public String jobDetailsApply(@PathVariable("id") Long id, Model model) {
        JobPostActivity jobDetails = jobPostActivityService.findById(id);

        //list store how many candidates applied/saved specific job
        List<JobSeekerApply> jobSeekerApplyList= jobSeekerApplyService.getCandidates(jobDetails);
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getJobCandidates(jobDetails);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)){
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
                //recruiter should see applied candidates information
                if (user != null){
                    model.addAttribute("applyList", jobSeekerApplyList);
                }
            } else {
                JobSeekerProfile user = jobSeekerProfileService.getCurrentJobSeekerProfile();
                if (user != null){
                    boolean exists = false;
                    boolean saved = false;
                    for (JobSeekerApply jobSeekerApply : jobSeekerApplyList) {
                        //see if this user applied/saved for this job
                        if (jobSeekerApply.getUserId().getId().equals(user.getId())) {
                            exists = true;
                            break;
                        }
                    }
                    for (JobSeekerSave jobSeekerSave : jobSeekerSaveList) {
                        if (jobSeekerSave.getUserId().getId().equals(user.getId())) {
                            saved = true;
                            break;
                        }
                    }
                    model.addAttribute("saved", saved);
                    model.addAttribute("exists", exists);
                }
            }
        }

        //new apply object for web form
        JobSeekerApply jobSeekerApply = new JobSeekerApply();
        model.addAttribute("applyJob",jobSeekerApply);


        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "job-details";
    }

    @PostMapping("job-details/apply/{id}")
    public String applyJob(@PathVariable("id") Long id, JobSeekerApply jobSeekerApply, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)){
            String name = auth.getName();
            Users user = userService.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.getJobSeekerProfileById(user.getId());
            JobPostActivity jobPostActivity = jobPostActivityService.findById(id);

            //post and profile is found.
            //create new apply record based on post and profile information.
            if (jobSeekerProfile.isPresent() && jobPostActivity != null) {
                //apply object contains follow information
                //and cover letter
                jobSeekerApply = new JobSeekerApply();
                jobSeekerApply.setUserId(jobSeekerProfile.get());
                jobSeekerApply.setJob((jobPostActivity));
                jobSeekerApply.setApplyDate(new Date());
            } else {
                throw new RuntimeException("User not found");
            }
            jobSeekerApplyService.addNew(jobSeekerApply);
        }
        return "redirect:/dashboard/";
    }

}

package com.myproject.jobapp.controller;

import com.myproject.jobapp.entity.JobPostActivity;
import com.myproject.jobapp.entity.RecruiterJobsDto;
import com.myproject.jobapp.entity.RecruiterProfile;
import com.myproject.jobapp.entity.Users;
import com.myproject.jobapp.service.JobPostActivityService;
import com.myproject.jobapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
public class JobPostActivityController {

    private final UserService userService;
    private final JobPostActivityService jobPostActivityService;

    @Autowired
    public JobPostActivityController(UserService userService, JobPostActivityService jobPostActivityService) {
        this.userService = userService;
        this.jobPostActivityService = jobPostActivityService;
    }

    @GetMapping("/dashboard/")
    public String searchForJobs(Model model){
        Object currentUser = userService.getCurrentUserProfile();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            String currentUserName = auth.getName();
            model.addAttribute("username", currentUserName);
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                //RecruiterJobsDto transfers data to model.
                List<RecruiterJobsDto>recruiterJobs = jobPostActivityService.getRecruiterJobs(((RecruiterProfile)currentUser).getId());
                model.addAttribute("jobPost", recruiterJobs);
            }
        }
        model.addAttribute("user", currentUser);
        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJob(Model model){
        model.addAttribute("user", userService.getCurrentUserProfile());
        model.addAttribute("jobPostActivity", new JobPostActivity());
        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String addNewJob(JobPostActivity jobPostActivity, Model model){
        Users user = userService.getCurrentUser();
        if (user != null){
            jobPostActivity.setPostById(user);
        }
        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("jobPostActivity", jobPostActivity);
        JobPostActivity saved = jobPostActivityService.save(jobPostActivity);
        return "redirect:/dashboard/";
    }

    @PostMapping("dashboard/edit/{id}")
    public String editJob(Model model, @PathVariable Long id){
        JobPostActivity jobPostActivity = jobPostActivityService.findById(id);
        model.addAttribute("jobPostActivity", jobPostActivity);
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "add-jobs";
    }


}

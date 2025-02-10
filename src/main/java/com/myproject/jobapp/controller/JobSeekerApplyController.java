package com.myproject.jobapp.controller;

import com.myproject.jobapp.entity.JobPostActivity;
import com.myproject.jobapp.service.JobPostActivityService;
import com.myproject.jobapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class JobSeekerApplyController {

    private final JobPostActivityService jobPostActivityService;
    private final UserService userService;

    public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UserService userService) {
        this.jobPostActivityService = jobPostActivityService;
        this.userService = userService;
    }

    @GetMapping("job-details-apply/{id}")
    public String jobDetailsApply(@PathVariable("id") Long id, Model model) {
        JobPostActivity jobDetails = jobPostActivityService.findById(id);

        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "job-details";
    }

}

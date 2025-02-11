package com.myproject.jobapp.controller;

import com.myproject.jobapp.entity.*;
import com.myproject.jobapp.service.JobPostActivityService;
import com.myproject.jobapp.service.JobSeekerApplyService;
import com.myproject.jobapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
public class JobPostActivityController {

    private final UserService userService;
    private final JobPostActivityService jobPostActivityService;
    private final JobSeekerApplyService jobSeekerApplyService;

    @Autowired
    public JobPostActivityController(UserService userService, JobPostActivityService jobPostActivityService, JobSeekerApplyService jobSeekerApplyService) {
        this.userService = userService;
        this.jobPostActivityService = jobPostActivityService;
        this.jobSeekerApplyService = jobSeekerApplyService;
    }

    @GetMapping("/dashboard/")
    public String searchForJobs(Model model,
                                @RequestParam(value = "job", required = false) String job,
                                @RequestParam(value = "location", required = false) String location,
                                @RequestParam(value = "partTime", required = false) String partTime,
                                @RequestParam(value = "fullTime", required = false) String fullTime,
                                @RequestParam(value = "freeLance", required = false) String freeLance,
                                @RequestParam(value = "remoteOnly", required = false) String remoteOnly,
                                @RequestParam(value = "officeOnly", required = false) String officeOnly,
                                @RequestParam(value = "partialRemote", required = false) String partialRemote,
                                @RequestParam(value = "today", required = false) boolean today,
                                @RequestParam(value = "days7", required = false) boolean days7,
                                @RequestParam(value = "days30", required = false) boolean days30) {

        // draw current username using userRep from authentication and use profileService to get profiles
        Object currentUser = userService.getCurrentUserProfile();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("partTime", Objects.equals(partTime,"Part-Time"));
        model.addAttribute("fullTime", Objects.equals(partTime,"Full-Time"));
        model.addAttribute("freelance", Objects.equals(partTime,"Freelance"));
        model.addAttribute("remoteOnly", Objects.equals(partTime,"Remote-Only"));
        model.addAttribute("officeOnly", Objects.equals(partTime,"Office-Only"));
        model.addAttribute("partialRemote", Objects.equals(partTime,"Partial-Remote"));
        model.addAttribute("today", today);
        model.addAttribute("days7", days7);
        model.addAttribute("days30", days30);
        model.addAttribute("job", job);
        model.addAttribute("location", location);

        LocalDate searchDate = null;
        List<JobPostActivity> jobPost = null;
        boolean dateSearchFlag = true;
        boolean remote = true;
        boolean type = true;;

        if (days30) {
            searchDate = LocalDate.now().minusDays(30);
        } else if (days7) {
            searchDate = LocalDate.now().minusDays(7);
        } else if (today) {
            searchDate = LocalDate.now();
        } else {
            dateSearchFlag = false;
        }

        if (partTime == null && fullTime == null && freeLance == null) {
            partTime = "Part-Time";
            fullTime = "Full-Time";
            freeLance = "Free-Lance";
            remote = false;
        }

        if (officeOnly == null && remoteOnly == null && partialRemote == null) {
            officeOnly = "Office-Only";
            remoteOnly = "Remote-Only";
            partialRemote = "Partial-Remote";
            type = false;
        }

        if (!dateSearchFlag && !remote && !type && !StringUtils.hasText(job) && !StringUtils.hasText(location)) {
            jobPost = jobPostActivityService.getAll();
        }
        else{
            jobPost = jobPostActivityService.search(job, location, Arrays.asList(partTime,fullTime,freeLance),
                    Arrays.asList(remoteOnly, officeOnly, partialRemote), searchDate);
        }


        if (!(auth instanceof AnonymousAuthenticationToken)){
            String currentUserName = auth.getName();
            model.addAttribute("username", currentUserName);
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                //RecruiterJobsDto transfers data to model.
                List<RecruiterJobsDto>recruiterJobs = jobPostActivityService.getRecruiterJobs(((RecruiterProfile) currentUser).getId());
                model.addAttribute("jobPost", recruiterJobs);
            } else {
                List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getCandidatesJobs((JobSeekerProfile) currentUser);
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

package com.myproject.jobapp.controller;

import com.myproject.jobapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobPostActivityController {

    private final UserService userService;

    @Autowired
    public JobPostActivityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard/")
    public String searchForJobs(Model model){
        Object currentUser = userService.getCurrentUserProfile();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            String currentUserName = auth.getName();
            model.addAttribute("username", currentUserName);
        }
        model.addAttribute("user", currentUser);
        return "dashboard";
    }

}

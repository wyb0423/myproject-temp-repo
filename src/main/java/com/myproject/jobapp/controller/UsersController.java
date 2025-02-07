package com.myproject.jobapp.controller;

import com.myproject.jobapp.entity.UserType;
import com.myproject.jobapp.entity.Users;
import com.myproject.jobapp.service.UserService;
import com.myproject.jobapp.service.UserTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UsersController {

    private final UserTypeService userTypeService;
    private final UserService userService;

    public UsersController(UserTypeService userTypeService, UserService userService) {
        this.userTypeService = userTypeService;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UserType> userTypes = userTypeService.findAll();
        model.addAttribute("allUserTypes", userTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String newUser(@Valid @ModelAttribute("user") Users user, BindingResult bindingResult, Model model) {
        System.out.println("Registered User - " + user);
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "This email address is already in use!");
            List<UserType> userTypes = userTypeService.findAll();
            model.addAttribute("allUserTypes", userTypes);
            model.addAttribute("user", new Users());
            return "register";
        }
        else {
            userService.save(user);
            return "redirect:/dashboard/";
        }
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}

package com.myproject.jobapp.controller;

import com.myproject.jobapp.entity.RecruiterProfile;
import com.myproject.jobapp.entity.Users;
import com.myproject.jobapp.repository.RecruiterProfileRepository;
import com.myproject.jobapp.repository.UserRepository;
import com.myproject.jobapp.service.RecruiterProfileService;
import com.myproject.jobapp.util.FileUploadUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UserRepository userRepository;
    private final RecruiterProfileService recruiterProfileService;

    public RecruiterProfileController(UserRepository userRepository, RecruiterProfileService recruiterProfileService) {
        this.userRepository = userRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)){
            String currentUserName = auth.getName();
            Users users = userRepository.findByEmail(currentUserName).orElseThrow(() -> new UsernameNotFoundException("Could not find user."));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.findById(users.getId());
            if (recruiterProfile.isPresent()) {
                model.addAttribute("profile", recruiterProfile.get());
            }

        }
        return "recruiter_profile";
    }

    @PostMapping("/addNew")
    //recruiterProfile is auto binded
    public String addNewRecruiterProfile(RecruiterProfile recruiterProfile, @RequestParam("image") MultipartFile multipartFile, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // find user according to recruiter
        if (!(auth instanceof AnonymousAuthenticationToken)){
            String currentUserName = auth.getName();
            Users users = userRepository.findByEmail(currentUserName).orElseThrow(() -> new UsernameNotFoundException("Could not find user."));
            recruiterProfile.setUserID(users);
            recruiterProfile.setId(users.getId());
        }
        model.addAttribute("profile", recruiterProfile);

        // assign profile file to recruiter object
        String filename = "";
        if (!multipartFile.getOriginalFilename().equals("")) {
            filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            recruiterProfile.setProfilePhoto(filename);
        }

        // persist object in db
        RecruiterProfile savedUser = recruiterProfileService.save(recruiterProfile);

        // save profile file in file system
        String uploadDir = "photos/recruiter-profile/" + savedUser.getId();
        try{
            FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/dashboard/";
    }
}

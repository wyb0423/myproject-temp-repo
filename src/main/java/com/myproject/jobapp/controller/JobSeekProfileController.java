package com.myproject.jobapp.controller;

import com.myproject.jobapp.entity.JobSeekerProfile;
import com.myproject.jobapp.entity.Skills;
import com.myproject.jobapp.entity.Users;
import com.myproject.jobapp.repository.JobSeekerProfileRepository;
import com.myproject.jobapp.repository.UserRepository;
import com.myproject.jobapp.service.JobSeekerProfileService;
import com.myproject.jobapp.service.UserService;
import com.myproject.jobapp.util.FileDownloadUtil;
import com.myproject.jobapp.util.FileUploadUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekProfileController {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private JobSeekerProfileService jobSeekerProfileService;
    private UserService userService;

    public JobSeekProfileController(JobSeekerProfileService jobSeekerProfileService, UserService userService, UserRepository userRepository, JobSeekerProfileRepository jobSeekerProfileRepository) {
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
    }

    @GetMapping("/")
    public String JobSeekerProfile(Model model) {
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            Users currUser = userRepository.findByEmail(auth.getName()).orElseThrow(()
                    -> new UsernameNotFoundException("User not found."));
            Optional<JobSeekerProfile> profile = jobSeekerProfileService.getJobSeekerProfileById(currUser.getId());
            if (profile.isPresent()) {
                jobSeekerProfile = profile.get();
                if (jobSeekerProfile.getSkills().isEmpty()) {
                    skills.add(new Skills());
                    jobSeekerProfile.setSkills(skills);
                }
            }

            model.addAttribute("skills", skills);
            model.addAttribute("profile", jobSeekerProfile);
        }

        return "job-seeker-profile";
    }

    //passed in a profile by the web form
    @PostMapping("/addNew")
    public String addNewProfile(JobSeekerProfile jobSeekerProfile,
                                Model model,
                                @RequestParam("image") MultipartFile image,
                                @RequestParam("pdf") MultipartFile pdf) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)){
                Users currUser = userRepository.findByEmail(auth.getName()).orElseThrow(()
                        -> new UsernameNotFoundException("User not found."));
                //build mapping between user and user-profile cuz we are adding new profile
                jobSeekerProfile.setId(currUser.getId());
                jobSeekerProfile.setUserID(currUser);
            }
            List<Skills> skills = new ArrayList<>();
            model.addAttribute("skills", skills);
            model.addAttribute("profile", jobSeekerProfile);

            for (Skills skill : jobSeekerProfile.getSkills()) {
                skill.setJobSeeker(jobSeekerProfile);
            }

            String imageName = "";
            String resumeName = "";

            if (!Objects.equals(image.getOriginalFilename(), "")) {
                imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
                jobSeekerProfile.setProfilePhoto(imageName);
            }

            if (!Objects.equals(pdf.getOriginalFilename(), "")) {
                resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
                jobSeekerProfile.setResume(resumeName);
            }

            JobSeekerProfile savedProfile = jobSeekerProfileService.save(jobSeekerProfile);

            //save file in file system
            try {
                String photoUpLoadDir = "photos/candidates/" + jobSeekerProfile.getId();
                if (!Objects.equals(image.getOriginalFilename(), "")) {
                    FileUploadUtil.saveFile(photoUpLoadDir,imageName,image);

                }
                if (!Objects.equals(pdf.getOriginalFilename(), "")) {
                    FileUploadUtil.saveFile(photoUpLoadDir,resumeName,pdf);

                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

            return "redirect:/dashboard/";
    }

    @GetMapping("/{id}")
    public String candidateProfile(@PathVariable("id") int id, Model model) {
        Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getJobSeekerProfileById(id);
        model.addAttribute("profile", seekerProfile.get());
        return "job-seeker-profile";
    }

    @GetMapping("/downloadResume")
    public ResponseEntity<?> downloadResume(@RequestParam(value = "fileName") String filename,
                                            @RequestParam(value = "userID") String userId) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();
        Resource resource = null;
        try{
            resource = downloadUtil.getFileAsResource("photos/candidates/" + userId,filename);
        } catch (IOException e){
            return ResponseEntity.badRequest().build();
        }
        if (resource == null) {
            return new ResponseEntity<>("file not found",HttpStatus.NOT_FOUND);
        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,headerValue).body(resource);
    }

}

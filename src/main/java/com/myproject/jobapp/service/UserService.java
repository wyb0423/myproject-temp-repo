package com.myproject.jobapp.service;

import com.myproject.jobapp.entity.JobSeekerProfile;
import com.myproject.jobapp.entity.RecruiterProfile;
import com.myproject.jobapp.entity.Users;
import com.myproject.jobapp.repository.JobSeekerRepository;
import com.myproject.jobapp.repository.RecruiterProfileRepository;
import com.myproject.jobapp.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JobSeekerRepository jobSeekerRepository, RecruiterProfileRepository recruiterProfileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users save(Users user) {
        // set properties before saving
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = userRepository.save(user);
        int userTypeId = savedUser.getUserTypeId().getId();
        if (userTypeId == 1){
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }
        else{
            jobSeekerRepository.save(new JobSeekerProfile(savedUser));
        }

        return savedUser;
    }

    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
            int userId = users.getId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
                return recruiterProfile;
            }
            else{
                JobSeekerProfile jobSeekerProfile = jobSeekerRepository.findById(userId).orElse(new JobSeekerProfile());
                return jobSeekerProfile;
            }
        }
        return null;

    }
}

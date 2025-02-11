package com.myproject.jobapp.service;

import com.myproject.jobapp.entity.RecruiterProfile;
import com.myproject.jobapp.entity.Users;
import com.myproject.jobapp.repository.RecruiterProfileRepository;
import com.myproject.jobapp.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UserRepository userRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository, UserRepository userRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.userRepository = userRepository;
    }

    public Optional<RecruiterProfile> findById(Integer id) {
        return recruiterProfileRepository.findById(id);
    }

    public RecruiterProfile save(RecruiterProfile recruiterProfile) {
        return recruiterProfileRepository.save(recruiterProfile);
    }

    public RecruiterProfile getCurrentRecruiterProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RecruiterProfile recruiterProfile = null;
        if (!(auth instanceof AnonymousAuthenticationToken)){
            Users user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            recruiterProfile = recruiterProfileRepository.findById(user.getId()).orElse(null);
        }
        return recruiterProfile;
    }
}

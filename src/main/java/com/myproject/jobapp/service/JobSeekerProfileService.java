package com.myproject.jobapp.service;

import com.myproject.jobapp.entity.JobSeekerProfile;
import com.myproject.jobapp.entity.RecruiterProfile;
import com.myproject.jobapp.entity.Users;
import com.myproject.jobapp.repository.JobSeekerProfileRepository;
import com.myproject.jobapp.repository.JobSeekerRepository;
import com.myproject.jobapp.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobSeekerProfileService {

    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UserRepository userRepository;

    public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepository, UserRepository userRepository) {
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.userRepository = userRepository;
    }

    //Ge job seeker's profile based on id
    public Optional<JobSeekerProfile> getJobSeekerProfileById(int id) {
        return jobSeekerProfileRepository.findById(id);
    }

    public JobSeekerProfile save(JobSeekerProfile jobSeekerProfile) {
        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }

    public JobSeekerProfile getCurrentJobSeekerProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JobSeekerProfile JobSeekerProfile = null;
        if (!(auth instanceof AnonymousAuthenticationToken)){
            Users user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            JobSeekerProfile = jobSeekerProfileRepository.findById(user.getId()).orElse(null);
        }
        return JobSeekerProfile;
    }
}

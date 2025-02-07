package com.myproject.jobapp.service;

import com.myproject.jobapp.entity.RecruiterProfile;
import com.myproject.jobapp.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {
    private final RecruiterProfileRepository recruiterProfileRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Optional<RecruiterProfile> findById(Integer id) {
        return recruiterProfileRepository.findById(id);
    }

    public RecruiterProfile save(RecruiterProfile recruiterProfile) {
        return recruiterProfileRepository.save(recruiterProfile);
    }
}

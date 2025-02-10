package com.myproject.jobapp.service;

import com.myproject.jobapp.entity.JobSeekerProfile;
import com.myproject.jobapp.repository.JobSeekerProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobSeekerProfileService {

    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepository) {
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
    }

    //Ge job seeker's profile based on id
    public Optional<JobSeekerProfile> getJobSeekerProfileById(int id) {
        return jobSeekerProfileRepository.findById(id);
    }

    public JobSeekerProfile save(JobSeekerProfile jobSeekerProfile) {
        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }

}

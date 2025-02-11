package com.myproject.jobapp.service;

import com.myproject.jobapp.entity.JobPostActivity;
import com.myproject.jobapp.entity.JobSeekerProfile;
import com.myproject.jobapp.entity.JobSeekerSave;
import com.myproject.jobapp.repository.JobSeekerRepository;
import com.myproject.jobapp.repository.JobSeekerSaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveService {

    private final JobSeekerSaveRepository jobSeekerSaveRepository;

    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getCandidateJobs(JobSeekerProfile jobSeekerProfile) {
        return jobSeekerSaveRepository.findByUserId(jobSeekerProfile);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity jobPostActivity) {
        return jobSeekerSaveRepository.findByJob(jobPostActivity);
    }

    public JobSeekerSave save(JobSeekerSave jobSeekerSave) {
        return jobSeekerSaveRepository.save(jobSeekerSave);
    }
}

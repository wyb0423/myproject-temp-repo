package com.myproject.jobapp.service;

import com.myproject.jobapp.entity.JobPostActivity;
import com.myproject.jobapp.entity.JobSeekerApply;
import com.myproject.jobapp.entity.JobSeekerProfile;
import com.myproject.jobapp.repository.JobSeekerApplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerApplyService {
    private final JobSeekerApplyRepository jobSeekerApplyRepository;

    public JobSeekerApplyService(JobSeekerApplyRepository jobSeekerApplyRepository) {
        this.jobSeekerApplyRepository = jobSeekerApplyRepository;
    }

    //receive a candidate and returns associated applies
    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile jobSeekerProfile){
        return jobSeekerApplyRepository.findByUserId(jobSeekerProfile);
    }

    //receive a job post and returns associated applies
    public List<JobSeekerApply> getCandidates(JobPostActivity jobPostActivity){
        return jobSeekerApplyRepository.findByJob(jobPostActivity);
    }

    public JobSeekerApply addNew(JobSeekerApply jobSeekerApply) {
        return jobSeekerApplyRepository.save(jobSeekerApply);
    }
}

package com.myproject.jobapp.service;

import com.myproject.jobapp.entity.*;
import com.myproject.jobapp.repository.JobPostActivityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity save(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }

    // dto stands for data transfer object
    // return a list of available jobs(information contained)
    public List<RecruiterJobsDto> getRecruiterJobs(int recruiterId){
        List<IRecruiterJob> recruiterJobsDtos = jobPostActivityRepository.getRecruiterJob(recruiterId);
        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();

        for (IRecruiterJob rec : recruiterJobsDtos) {
            JobLocation location = new JobLocation(rec.getLocationId(),rec.getCity(),rec.getCountry(),rec.getState());
            JobCompany company = new JobCompany(rec.getCompanyId(),"",rec.getName());
            recruiterJobsDtoList.add(new RecruiterJobsDto(rec.getTotalCandidates(),rec.getJobPostId(),rec.getJobTitle(),location,company));
        }
        return recruiterJobsDtoList;
    }

    public JobPostActivity findById(Long id) {
        System.out.println("finding post - " + id);
        return jobPostActivityRepository.findById(id).orElseThrow(()->new RuntimeException("Post not found."));
    }

}

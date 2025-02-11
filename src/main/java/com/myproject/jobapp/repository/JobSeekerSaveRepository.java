package com.myproject.jobapp.repository;

import com.myproject.jobapp.entity.JobPostActivity;
import com.myproject.jobapp.entity.JobSeekerProfile;
import com.myproject.jobapp.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave,Integer> {
    
    List<JobSeekerSave> findByUserId(JobSeekerProfile userId);

    List<JobSeekerSave> findByJob(JobPostActivity job);

    JobSeekerProfile job(JobPostActivity job);
}

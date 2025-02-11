package com.myproject.jobapp.repository;

import com.myproject.jobapp.entity.JobPostActivity;
import com.myproject.jobapp.entity.JobSeekerApply;
import com.myproject.jobapp.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply,Long> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);
    List<JobSeekerApply> findByJob(JobPostActivity job);
}

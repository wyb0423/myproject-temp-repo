package com.myproject.jobapp.repository;

import com.myproject.jobapp.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile,Integer> {
}

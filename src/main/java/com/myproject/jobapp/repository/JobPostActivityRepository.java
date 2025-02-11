package com.myproject.jobapp.repository;

import com.myproject.jobapp.entity.IRecruiterJob;
import com.myproject.jobapp.entity.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Long> {


    // retrieve needed data from db
    // store in IRecruiterJob object
    @Query(value = " SELECT COUNT(s.user_id) as totalCandidates,j.job_post_id as jobPostId,j.job_title as jobTitle,l.location_id as locationId,l.city,l.state,l.country,c.company_id as companyId,c.name" +
            " FROM job_post_activity j " +
            " inner join job_location l " +
            " on j.job_location_id = l.location_id " +
            " INNER join job_company c  " +
            " on j.job_company_id = c.company_id " +
            " left join job_seeker_apply s " +
            " on s.job_id = j.job_post_id " +
            " where j.posted_by_id = :recruiter " +
            " GROUP By j.job_post_id" ,nativeQuery = true)
    List<IRecruiterJob> getRecruiterJob(@Param("recruiter") int recruiter);

    @Query(value = "SELECT * FROM job_post_activity j INNER JOIN job_location l on j.job_location_id=l.location_id  WHERE j" +
            ".job_title LIKE %:job%"
            + " AND (l.city LIKE %:location%"
            + " OR l.country LIKE %:location%"
            + " OR l.state LIKE %:location%) " +
            " AND (j.job_type IN(:type)) " +
            " AND (j.remote IN(:remote)) ", nativeQuery = true)
    List<JobPostActivity> searchWithoutDate(@Param("job") String job,
                                            @Param("location") String location,
                                            @Param("remote") List<String> remote,
                                            @Param("type") List<String> type);
    @Query(value = "SELECT * FROM job_post_activity j INNER JOIN job_location l on j.job_location_id=l.location_id  WHERE j" +
            ".job_title LIKE %:job%"
            + " AND (l.city LIKE %:location%"
            + " OR l.country LIKE %:location%"
            + " OR l.state LIKE %:location%) " +
            " AND (j.job_type IN(:type)) " +
            " AND (j.remote IN(:remote)) " +
            " AND (j.posted_date >= :searchDate)", nativeQuery = true)
    List<JobPostActivity> search(@Param("job") String job,
                                 @Param("location") String location,
                                 @Param("remote") List<String> remote,
                                 @Param("type") List<String> type,
                                 @Param("date") LocalDate searchDate);
}

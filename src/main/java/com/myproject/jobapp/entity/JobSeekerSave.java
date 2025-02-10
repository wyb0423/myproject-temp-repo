package com.myproject.jobapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name="job_seeker_save",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userId","job"})})
public class JobSeekerSave {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer Id;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private JobSeekerProfile userId;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="job_id")
    private JobPostActivity job;

    public JobSeekerSave() {
    }

    public JobSeekerSave(Integer id, JobSeekerProfile userId, JobPostActivity job) {
        Id = id;
        this.userId = userId;
        this.job = job;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public JobSeekerProfile getUserId() {
        return userId;
    }

    public void setUserId(JobSeekerProfile userId) {
        this.userId = userId;
    }

    public JobPostActivity getJob() {
        return job;
    }

    public void setJob(JobPostActivity job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "JobSeekerSave{" +
                "Id=" + Id +
                ", userId=" + userId.toString() +
                ", job=" + job.toString() +
                '}';
    }
}

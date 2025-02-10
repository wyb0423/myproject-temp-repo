package com.myproject.jobapp.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name="job_post_activity")
public class JobPostActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="job_post_id")
    private int id;

    @Column(name="job_description")
    @Length(max=10000)
    private String jobDescription;

    @Column(name="job_title")
    private String jobTitle;

    @Column(name="job_type")
    private String jobType;

    @Column(name="posted_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postedDate;

    @Column(name="remote")
    private String remote;

    @Column(name="salary")
    private String salary;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="job_company_id")
    private JobCompany jobCompany;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="job_location_id")
    private JobLocation jobLocation;

    @ManyToOne
    @JoinColumn(name="posted_by_id")
    private Users postById;

    @Transient
    private Boolean isActive;

    @Transient
    private Boolean isSaved;

    public JobPostActivity() {
    }

    public JobPostActivity(int id, String jobDescription, String jobTitle, String jobType, Date postedDate, String remote, String salary, JobCompany jobCompany, JobLocation jobLocation, Users postById) {
        this.id = id;
        this.jobDescription = jobDescription;
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.postedDate = postedDate;
        this.remote = remote;
        this.salary = salary;
        this.jobCompany = jobCompany;
        this.jobLocation = jobLocation;
        this.postById = postById;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public JobCompany getJobCompany() {
        return jobCompany;
    }

    public void setJobCompany(JobCompany jobCompany) {
        this.jobCompany = jobCompany;
    }

    public JobLocation getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(JobLocation jobLocation) {
        this.jobLocation = jobLocation;
    }

    public Users getPostById() {
        return postById;
    }

    public void setPostById(Users postById) {
        this.postById = postById;
    }

    @Override
    public String toString() {
        return "JobPostActivity{" +
                "id=" + id +
                ", jobDescription='" + jobDescription + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobType='" + jobType + '\'' +
                ", postedDate=" + postedDate +
                ", remote='" + remote + '\'' +
                ", salary='" + salary + '\'' +
                ", jobCompany=" + jobCompany +
                ", jobLocation=" + jobLocation +
                ", postById=" + postById +
                '}';
    }
}

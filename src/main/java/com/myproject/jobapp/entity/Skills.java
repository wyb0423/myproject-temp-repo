package com.myproject.jobapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name="skills")
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="skill_name")
    private String skillName;

    @Column(name="skill_experience")
    private String skillExperience;

    @Column(name="skill_level")
    private String skillLevel;

    @JoinColumn(name="job_seeker_profile")
    @ManyToOne(cascade = CascadeType.ALL)
    private JobSeekerProfile jobSeeker;

    public Skills() {
    }

    public Skills(int id, String skillName, String skillExperience, String skillLevel, JobSeekerProfile jobSeeker) {
        this.id = id;
        this.skillName = skillName;
        this.skillExperience = skillExperience;
        this.skillLevel = skillLevel;
        this.jobSeeker = jobSeeker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillExperience() {
        return skillExperience;
    }

    public void setSkillExperience(String skillExperience) {
        this.skillExperience = skillExperience;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public JobSeekerProfile getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeekerProfile jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    @Override
    public String toString() {
        return "Skills{" +
                "skillLevel='" + skillLevel + '\'' +
                ", skillExperience='" + skillExperience + '\'' +
                ", skillName='" + skillName + '\'' +
                ", id=" + id +
                '}';
    }
}

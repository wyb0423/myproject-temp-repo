package com.myproject.jobapp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="job_seeker_profile")
public class JobSeekerProfile {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name="account_id")
    @MapsId
    private Users userID;

    @Column(name="city")
    private String city;

    @Column(name="country")
    private String country;

    @Column(name="state")
    private String state;

    @Column(name="employment_type")
    private String employmentType;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="profile_photo",nullable = true,length = 64)
    private String profilePhoto;

    @Column(name="resume")
    private String resume;

    @Column(name="work_authorization")
    private String workAuthorization;

    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL)
    private List<Skills> skills;

    public JobSeekerProfile() {
    }

    public JobSeekerProfile(Users user){
        this.userID = user;
    }

    public JobSeekerProfile(Integer id, Users userID, String city, String country, String state, String employmentType, String firstName, String lastName, String profilePhoto, String resume, String workAuthorization, List<Skills> skills) {
        this.id = id;
        this.userID = userID;
        this.city = city;
        this.country = country;
        this.state = state;
        this.employmentType = employmentType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhoto = profilePhoto;
        this.resume = resume;
        this.workAuthorization = workAuthorization;
        this.skills = skills;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getWorkAuthorization() {
        return workAuthorization;
    }

    public void setWorkAuthorization(String workAuthorization) {
        this.workAuthorization = workAuthorization;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    @Transient
    public String getPhotosImagePath(){
        if (profilePhoto == null || id == null){
            return null;
        }
        return "/photos/candidates/" + id + "/" + profilePhoto;
    }

    @Override
    public String toString() {
        return "JobSeekerProfile{" +
                "id=" + id +
                ", userID=" + userID +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", resume='" + resume + '\'' +
                ", workAuthorization='" + workAuthorization + '\'' +
                '}';
    }
}

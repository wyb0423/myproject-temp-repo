package com.myproject.jobapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name="recruiter_profile")
public class RecruiterProfile {

    @Id
    private int id;

    // Some mapping relations
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

    @Column(name="company")
    private String company;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="profile_photo",nullable = true,length = 64)
    private String profilePhoto;

    @Transient
    public String getPhotosImagePath(){
        if (profilePhoto != null) {
            return "/photo/recruiter-profile/" + id + "/" + profilePhoto;
        }
        return null;
    }

    public RecruiterProfile() {
    }

    public RecruiterProfile(Users user){
        this.userID = user;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public RecruiterProfile(int id, String city, String country, String state, String company, String firstName, String lastName, String profilePhoto) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.state = state;
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhoto = profilePhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    @Override
    public String toString() {
        return "RecruiterProfile{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", company='" + company + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}

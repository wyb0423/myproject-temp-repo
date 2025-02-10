package com.myproject.jobapp.entity;

public interface IRecruiterJob {

    Long getTotalCandidates();

    int getJobPostId();

    String getJobTitle();

    int getLocationId();

    String getCity();

    String getState();

    String getCountry();

    int getCompanyId();

    String getName();
}

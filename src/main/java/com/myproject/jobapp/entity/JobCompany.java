package com.myproject.jobapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name="job_company")
public class JobCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id")
    private int id;

    @Column(name="logo")
    private String companyLogo;

    @Column(name="name")
    private String companyName;

    public JobCompany() {
    }

    public JobCompany(int id, String companyLogo, String companyName) {
        this.id = id;
        this.companyLogo = companyLogo;
        this.companyName = companyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "JobCompany{" +
                "id=" + id +
                ", companyLogo='" + companyLogo + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}

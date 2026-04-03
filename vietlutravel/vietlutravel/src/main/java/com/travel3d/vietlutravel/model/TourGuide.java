package com.travel3d.vietlutravel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TourGuides")
public class TourGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GuideID")
    private Integer guideId;

    @Column(name = "FullName", nullable = false)
    private String fullName;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "ExperienceYears")
    private Integer experienceYears;

    @Column(name = "Language")
    private String language;

    @Column(name = "Status")
    private String status = "AVAILABLE";

    public TourGuide() {
    }

    public Integer getGuideId() {
        return guideId;
    }

    public void setGuideId(Integer guideId) {
        this.guideId = guideId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

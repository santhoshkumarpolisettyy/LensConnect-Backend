package com.photographerplatform.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "photographers")
public class Photographer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String businessName;
    private String email;

    @JsonIgnore
    private String password;

    private String phoneNumber;

    private String city;
    private String pincode;

    private String specialization;
    private int experienceYears;

    private String bio;

    private double startingPrice;

    private double rating = 0.0;
    private int totalReviews = 0;

    // ========================================
    // PROFILE PHOTO
    // ========================================

    @Lob
    @JsonIgnore
    private byte[] profilePhoto;

    private String profilePhotoContentType;

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public Photographer() {
    }

    // ========================================
    // ID
    // ========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ========================================
    // FULL NAME
    // ========================================

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // ========================================
    // BUSINESS NAME
    // ========================================

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    // ========================================
    // EMAIL
    // ========================================

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // ========================================
    // PASSWORD
    // ========================================

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ========================================
    // PHONE NUMBER
    // ========================================

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // ========================================
    // CITY
    // ========================================

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // ========================================
    // PINCODE
    // ========================================

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    // ========================================
    // SPECIALIZATION
    // ========================================

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // ========================================
    // EXPERIENCE
    // ========================================

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    // ========================================
    // BIO
    // ========================================

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // ========================================
    // STARTING PRICE
    // ========================================

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    // ========================================
    // RATING
    // ========================================

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    // ========================================
    // TOTAL REVIEWS
    // ========================================

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    // ========================================
    // PROFILE PHOTO
    // ========================================

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    // ========================================
    // PROFILE PHOTO CONTENT TYPE
    // ========================================

    public String getProfilePhotoContentType() {
        return profilePhotoContentType;
    }

    public void setProfilePhotoContentType(
            String profilePhotoContentType) {

        this.profilePhotoContentType = profilePhotoContentType;
    }
}
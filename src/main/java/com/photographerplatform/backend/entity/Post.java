package com.photographerplatform.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "photographer_posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long photographerId;

    private String title;

    private String description;

    private String serviceType;

    private double price;

    public Post() {
    }

    // ================================
    // ID
    // ================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ================================
    // PHOTOGRAPHER ID
    // ================================

    public Long getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(Long photographerId) {
        this.photographerId = photographerId;
    }

    // ================================
    // TITLE
    // ================================

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // ================================
    // DESCRIPTION
    // ================================

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ================================
    // SERVICE TYPE
    // ================================

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    // ================================
    // PRICE
    // ================================

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
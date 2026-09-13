package com.photographerplatform.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photographerplatform.backend.entity.PortfolioItem;

public interface PortfolioRepository
        extends JpaRepository<PortfolioItem, Long> {

    List<PortfolioItem> findByPhotographerId(Long photographerId);
}
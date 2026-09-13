package com.photographerplatform.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.photographerplatform.backend.entity.PortfolioItem;
import com.photographerplatform.backend.repository.PortfolioRepository;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioService(
            PortfolioRepository portfolioRepository) {

        this.portfolioRepository = portfolioRepository;
    }

    // ========================================
    // UPLOAD PORTFOLIO PHOTO
    // ========================================

    public PortfolioItem uploadPhoto(
            Long photographerId,
            String title,
            MultipartFile photo) throws Exception {

        PortfolioItem item = new PortfolioItem();

        item.setPhotographerId(photographerId);

        item.setTitle(title);

        item.setPhoto(photo.getBytes());

        item.setContentType(photo.getContentType());

        return portfolioRepository.save(item);
    }

    // ========================================
    // GET PHOTOGRAPHER PORTFOLIO
    // ========================================

    public List<PortfolioItem> getPortfolio(
            Long photographerId) {

        return portfolioRepository
                .findByPhotographerId(photographerId);
    }

    // ========================================
    // GET PORTFOLIO PHOTO BY ID
    // ========================================

    public PortfolioItem getPortfolioItem(
            Long id) {

        return portfolioRepository
                .findById(id)
                .orElse(null);
    }

    // ========================================
    // DELETE PORTFOLIO PHOTO
    // ========================================

    public boolean deletePhoto(Long id) {

        if (!portfolioRepository.existsById(id)) {
            return false;
        }

        portfolioRepository.deleteById(id);

        return true;
    }
}
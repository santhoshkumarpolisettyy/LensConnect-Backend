package com.photographerplatform.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.photographerplatform.backend.entity.PortfolioItem;
import com.photographerplatform.backend.service.PortfolioService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(
            PortfolioService portfolioService) {

        this.portfolioService = portfolioService;
    }

    // ========================================
    // UPLOAD PORTFOLIO PHOTO
    // ========================================

    @PostMapping(
            value = "/{photographerId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadPortfolioPhoto(

            @PathVariable Long photographerId,

            @RequestParam("title") String title,

            @RequestParam("photo") MultipartFile photo) {

        if (photo == null || photo.isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please select a portfolio photo");
        }

        if (title == null || title.isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body("Portfolio title is required");
        }

        try {

            PortfolioItem item =
                    portfolioService.uploadPhoto(
                            photographerId,
                            title,
                            photo
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(item);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload portfolio photo");
        }
    }

    // ========================================
    // GET PHOTOGRAPHER PORTFOLIO
    // ========================================

    @GetMapping("/{photographerId}")
    public ResponseEntity<?> getPortfolio(

            @PathVariable Long photographerId) {

        List<PortfolioItem> portfolio =
                portfolioService.getPortfolio(
                        photographerId
                );

        return ResponseEntity.ok(portfolio);
    }

    // ========================================
    // GET SINGLE PORTFOLIO ITEM
    // ========================================

    @GetMapping("/item/{id}")
    public ResponseEntity<?> getPortfolioItem(

            @PathVariable Long id) {

        PortfolioItem item =
                portfolioService.getPortfolioItem(id);

        if (item == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Portfolio item not found");
        }

        return ResponseEntity.ok(item);
    }

    // ========================================
    // GET PORTFOLIO PHOTO
    // ========================================

    @GetMapping("/item/{id}/photo")
    public ResponseEntity<?> getPortfolioPhoto(

            @PathVariable Long id) {

        PortfolioItem item =
                portfolioService.getPortfolioItem(id);

        if (item == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Portfolio item not found");
        }

        if (item.getPhoto() == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Portfolio photo not found");
        }

        String contentType =
                item.getContentType();

        if (contentType == null ||
                contentType.isBlank()) {

            contentType = "image/jpeg";
        }

        return ResponseEntity
                .ok()
                .contentType(
                        MediaType.parseMediaType(
                                contentType
                        )
                )
                .body(item.getPhoto());
    }

    // ========================================
    // DELETE PORTFOLIO PHOTO
    // ========================================

    @DeleteMapping("/item/{id}")
    public ResponseEntity<?> deletePortfolioPhoto(

            @PathVariable Long id) {

        boolean deleted =
                portfolioService.deletePhoto(id);

        if (!deleted) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Portfolio item not found");
        }

        return ResponseEntity.ok(
                "Portfolio photo deleted successfully"
        );
    }
}
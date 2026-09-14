package com.photographerplatform.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photographerplatform.backend.entity.Review;
import com.photographerplatform.backend.service.ReviewService;

@CrossOrigin(origins = {
	    "http://localhost:5173",
	    "https://lensconnect-frontend.vercel.app"
	})
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Review review) {

        if (review.getPhotographerId() == null
                || review.getClientName() == null
                || review.getClientName().isBlank()
                || review.getRating() < 1
                || review.getRating() > 5
                || review.getComment() == null
                || review.getComment().isBlank()) {

            return ResponseEntity.badRequest()
                    .body("Photographer, client name, rating and comment are required");
        }

        try {
            Review createdReview = reviewService.createReview(review);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdReview);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<List<Review>> getPhotographerReviews(
            @PathVariable Long photographerId) {

        return ResponseEntity.ok(
                reviewService.getPhotographerReviews(photographerId)
        );
    }
}
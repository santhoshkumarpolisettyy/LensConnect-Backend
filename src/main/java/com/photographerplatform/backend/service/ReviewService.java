package com.photographerplatform.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.photographerplatform.backend.entity.Photographer;
import com.photographerplatform.backend.entity.Review;
import com.photographerplatform.backend.repository.PhotographerRepository;
import com.photographerplatform.backend.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PhotographerRepository photographerRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            PhotographerRepository photographerRepository) {

        this.reviewRepository = reviewRepository;
        this.photographerRepository = photographerRepository;
    }

    public Review createReview(Review review) {

        // Rating must be between 1 and 5
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Review savedReview = reviewRepository.save(review);

        updatePhotographerRating(review.getPhotographerId());

        return savedReview;
    }

    public List<Review> getPhotographerReviews(Long photographerId) {

        return reviewRepository
                .findByPhotographerIdOrderByCreatedAtDesc(photographerId);
    }

    private void updatePhotographerRating(Long photographerId) {

        List<Review> reviews =
                reviewRepository.findByPhotographerIdOrderByCreatedAtDesc(photographerId);

        if (reviews.isEmpty()) {
            return;
        }

        double totalRating = 0;

        for (Review review : reviews) {
            totalRating += review.getRating();
        }

        double averageRating = totalRating / reviews.size();

        Photographer photographer =
                photographerRepository.findById(photographerId).orElse(null);

        if (photographer != null) {

            photographer.setRating(
                    Math.round(averageRating * 10.0) / 10.0
            );

            photographer.setTotalReviews(reviews.size());

            photographerRepository.save(photographer);
        }
    }
}
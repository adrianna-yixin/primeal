package net.yixingong.dining.reviews.service;

import net.yixingong.dining.reviews.payload.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(Long restaurantId, ReviewDto reviewDto);
    ReviewDto getReviewById(Long restaurantId, Long reviewId);
    List<ReviewDto> getAllReviews();
    ReviewDto updateReview(Long restaurantId, Long reviewId, ReviewDto reviewDto);
    void deleteReview(Long restaurantId, Long reviewId);

    List<ReviewDto> getReviewsByRestaurantId(Long restaurantId);
}

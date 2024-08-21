package net.yixingong.dining.reviews.service.impl;

import net.yixingong.dining.reviews.payload.ReviewDto;
import net.yixingong.dining.reviews.exception.DiningReviewsAPIException;
import net.yixingong.dining.reviews.exception.ResourceNotFoundException;
import net.yixingong.dining.reviews.mapper.ReviewMapper;
import net.yixingong.dining.reviews.entity.Restaurant;
import net.yixingong.dining.reviews.entity.Review;
import net.yixingong.dining.reviews.repository.RestaurantRepository;
import net.yixingong.dining.reviews.repository.ReviewRepository;
import net.yixingong.dining.reviews.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private RestaurantRepository restaurantRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public ReviewDto createReview(Long restaurantId, ReviewDto reviewDto) {
        Review review = ReviewMapper.MAPPER.mapToReview(reviewDto);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));
        review.setRestaurant(restaurant);
        Review newReview = reviewRepository.save(review);
        return ReviewMapper.MAPPER.mapToReviewDto(newReview);
    }

    @Override
    public ReviewDto getReviewById(Long restaurantId, Long reviewId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        if(!review.getRestaurant().getId().equals(restaurant.getId())) {
            throw new DiningReviewsAPIException(HttpStatus.BAD_REQUEST, "The review does not belong to the restaurant.");
        }

        return ReviewMapper.MAPPER.mapToReviewDto(review);
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(ReviewMapper.MAPPER::mapToReviewDto).collect(Collectors.toList());
    }

    @Override
    public ReviewDto updateReview(Long restaurantId, Long reviewId, ReviewDto reviewRequest) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));

        validateReviewOwnership(review, restaurant);

        review.setUsername(reviewRequest.getUsername());
        review.setEmail(reviewRequest.getEmail());
        review.setRating(reviewRequest.getRating());
        review.setContent(reviewRequest.getContent());
        review.setRestaurant(restaurant);
        Review updatedReview = reviewRepository.save(review);
        return ReviewMapper.MAPPER.mapToReviewDto(updatedReview);
    }

    @Override
    public void deleteReview(Long restaurantId, Long reviewId) {
        Review existingReview = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));

        validateReviewOwnership(existingReview, restaurant);
        reviewRepository.delete(existingReview);
    }

    @Override
    public List<ReviewDto> getReviewsByRestaurantId(Long restaurantId) {
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
        return reviews.stream()
                .map(ReviewMapper.MAPPER::mapToReviewDto)
                .collect(Collectors.toList());
    }

    private void validateReviewOwnership(Review existingReview, Restaurant restaurant) {
        if (!existingReview.getRestaurant().getId().equals(restaurant.getId())) {
            throw new DiningReviewsAPIException(HttpStatus.BAD_REQUEST, "The review does not belong to the restaurant.");
        }
    }
}

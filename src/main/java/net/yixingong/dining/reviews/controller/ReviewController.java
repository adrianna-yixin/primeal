package net.yixingong.dining.reviews.controller;

import net.yixingong.dining.reviews.payload.ReviewDto;
import net.yixingong.dining.reviews.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(
        name = "CRUD REST APIs for Review Resource"
)
class ReviewController {

    @Value("$(spring.mail.username)")
    private String emailAddress;

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(
            summary = "Create Review REST API",
            description = "Create Review REST API is used to save a new review into database."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable Long restaurantId,
                                                  @Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto addedReviewDto = reviewService.createReview(restaurantId, reviewDto);

        return new ResponseEntity<>(addedReviewDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Review By Id REST API",
            description = "Get Review By Id REST API is used to get a single review from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/restaurants/{restaurantId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewsById(@PathVariable Long restaurantId,
                                                    @PathVariable("id") Long reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(restaurantId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Reviews REST API",
            description = "Get All Reviews REST API is used to get all reviews from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Review REST API",
            description = "Update Review REST API is used to update a particular review in the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/restaurants/{restaurantId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long restaurantId,
                                                  @PathVariable("id") Long reviewId,
                                                  @Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(restaurantId, reviewId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Review REST API",
            description = "Delete Review REST API is used to delete a particular review from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/restaurants/{restaurantId}/reviews/{id}")
    public ResponseEntity<String> deleteReviewById(@PathVariable Long restaurantId, @PathVariable("id") Long reviewId) {
        reviewService.deleteReview(restaurantId, reviewId);
        return new ResponseEntity<>("Review deleted successfully!", HttpStatus.OK);
    }

    @Operation(
            summary = "Get Review By Restaurant Id REST API",
            description = "Get Review By Restaurant Id REST API is used to get all the reviews of a particular restaurant from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsByRestaurantId(@PathVariable Long restaurantId) {
        List<ReviewDto> reviews = reviewService.getReviewsByRestaurantId(restaurantId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
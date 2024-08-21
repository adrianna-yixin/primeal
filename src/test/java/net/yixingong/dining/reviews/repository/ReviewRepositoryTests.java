package net.yixingong.dining.reviews.repository;

import net.yixingong.dining.reviews.entity.Restaurant;
import net.yixingong.dining.reviews.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void givenReviewObject_whenSave_thenReturnReview() {
        Restaurant restaurant = Restaurant.builder()
                .name("Marzo")
                .city("Dublin")
                .phoneNumber("851500000")
                .website("www.marzo.com")
                .postcode("D0P735")
                .build();
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        Review review = Review.builder()
                .username("James")
                .email("james@gmail.com")
                .content("It was good")
                .rating(7)
                .restaurant(savedRestaurant)
                .build();

        Review savedReview = reviewRepository.save(review);

        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void givenReviewObject_whenGetById_thenRetrieveReview() {
        Restaurant restaurant = Restaurant.builder()
                .name("Marzo")
                .city("Dublin")
                .phoneNumber("851500000")
                .website("www.marzo.com")
                .postcode("D0P735")
                .build();
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        Review review = Review.builder()
                .username("James")
                .email("james@gmail.com")
                .content("It was good")
                .rating(7)
                .restaurant(savedRestaurant)
                .build();
        reviewRepository.save(review);
        Review reviewDB = reviewRepository.findById(review.getId()).get();
        assertThat(reviewDB).isNotNull();
    }

    @Test
    public void givenReviewsList_whenFindByAll_thenReturnReviewList() {
        Restaurant restaurant = Restaurant.builder()
                .name("Marzo")
                .city("Dublin")
                .phoneNumber("851500000")
                .website("www.marzo.com")
                .postcode("D0P735")
                .build();
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        Review review1 = Review.builder()
                .username("James")
                .email("james@gmail.com")
                .content("It was good")
                .rating(7)
                .restaurant(savedRestaurant)
                .build();
        reviewRepository.save(review1);
        Review review2 = Review.builder()
                .username("Jane")
                .email("jane@gmail.com")
                .content("I quite enjoyed it")
                .rating(8)
                .restaurant(savedRestaurant)
                .build();
        reviewRepository.save(review2);
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList.size()).isEqualTo(2);
    }

    @Test
    public void givenReviewObject_whenUpdate_thenReturnReview() {
        Restaurant restaurant = Restaurant.builder()
                .name("Marzo")
                .city("Dublin")
                .phoneNumber("851500000")
                .website("www.marzo.com")
                .postcode("D0P735")
                .build();
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        Review review = Review.builder()
                .username("James")
                .email("james@gmail.com")
                .content("It was good")
                .rating(7)
                .restaurant(savedRestaurant)
                .build();
        reviewRepository.save(review);

        Review savedReview = reviewRepository.findById(review.getId()).get();
        savedReview.setEmail("james@yahoo.com");
        savedReview.setRating(6);
        Review updatedReview = reviewRepository.save(savedReview);
        assertThat(updatedReview.getEmail()).isEqualTo("james@yahoo.com");
        assertThat(updatedReview.getRating()).isEqualTo(6);
    }

    @Test
    public void givenReviewObject_whenDeleteById_thenRemoveReview() {
        Restaurant restaurant = Restaurant.builder()
                .name("Marzo")
                .city("Dublin")
                .phoneNumber("851500000")
                .website("www.marzo.com")
                .postcode("D0P735")
                .build();
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        Review review = Review.builder()
                .username("James")
                .email("james@gmail.com")
                .content("It was good")
                .rating(7)
                .restaurant(savedRestaurant)
                .build();
        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());
        Optional<Review> reviewOptional = reviewRepository.findById(review.getId());
        assertThat(reviewOptional).isEmpty();
    }
}

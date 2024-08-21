package net.yixingong.dining.reviews.repository;

import net.yixingong.dining.reviews.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select r from Restaurant r where r.name = :name")
    Optional<Restaurant> findByName(@Param("name") String name);
    @Query("select r from Restaurant r where r.postcode = :postcode")
    Optional<Restaurant> findByPostcode(@Param("postcode") String postcode);
    @Query("select r from Restaurant r where r.city = :city")
    List<Restaurant> findByCity(@Param("city") String city);
    @Query("select r from Restaurant r where r.category.id = :categoryId")
    List<Restaurant> findByCategoryId(@Param("categoryId") Long categoryId);
}

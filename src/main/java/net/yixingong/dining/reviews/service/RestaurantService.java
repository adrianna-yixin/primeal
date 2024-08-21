package net.yixingong.dining.reviews.service;

import net.yixingong.dining.reviews.payload.RestaurantDto;
import net.yixingong.dining.reviews.payload.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    // Basic
    RestaurantDto addRestaurant(RestaurantDto restaurantDto);
    RestaurantDto getRestaurantById(Long id);
    RestaurantResponse getAllRestaurants(int pageNo, int pageSize, String sortBy, String sortDir);
    RestaurantDto updateRestaurant(RestaurantDto restaurantDto);
    void deleteRestaurant(Long restaurantId);

    // Additional
    RestaurantDto findByName(String name);
    RestaurantDto findByPostcode(String postcode);
    List<RestaurantDto> findByCity(String city);
    List<RestaurantDto> findByCategoryId(Long categoryId);
}

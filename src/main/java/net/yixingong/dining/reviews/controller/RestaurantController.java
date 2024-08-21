package net.yixingong.dining.reviews.controller;

import net.yixingong.dining.reviews.payload.RestaurantDto;
import net.yixingong.dining.reviews.payload.RestaurantResponse;
import net.yixingong.dining.reviews.service.RestaurantService;
import net.yixingong.dining.reviews.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
class RestaurantController {

    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(
            summary = "Add Restaurant REST API",
            description = "Add Restaurant REST API is used to save a new restaurant into database."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RestaurantDto> addRestaurant(@Valid @RequestBody RestaurantDto restaurant) {
        RestaurantDto newRestaurant = restaurantService.addRestaurant(restaurant);
        return new ResponseEntity<>(newRestaurant, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Restaurant By Id REST API",
            description = "Get Restaurant By Id REST API is used to get a single restaurant from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable Long id) {
        RestaurantDto restaurant = restaurantService.getRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Restaurants REST API",
            description = "Get All Restaurants REST API is used to get all restaurants from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public RestaurantResponse getAllRestaurants(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return restaurantService.getAllRestaurants(pageNo, pageSize, sortBy, sortDir);
    }

    @Operation(
            summary = "Update Restaurant REST API",
            description = "Update Restaurant REST API is used to update a particular restaurant in the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable Long id,
                                                          @Valid @RequestBody RestaurantDto restaurant) {
        restaurant.setId(id);
        RestaurantDto updatedRestaurant = restaurantService.updateRestaurant(restaurant);
        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Restaurant REST API",
            description = "Delete Restaurant REST API is used to delete a particular restaurant from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>("Restaurant deleted successfully!", HttpStatus.OK);
    }

    @Operation(
            summary = "Find Restaurant By Name REST API",
            description = "Find Restaurant By Name REST API is used to get a particular restaurant by name from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/by-name/{name}")
    public ResponseEntity<RestaurantDto> findRestaurantByName(@PathVariable("name") String restaurantName) {
        RestaurantDto restaurant = restaurantService.findByName(restaurantName);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @Operation(
            summary = "Find Restaurant By Postcode REST API",
            description = "Find Restaurant By Postcode REST API is used to get a particular restaurant by postcode from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/by-postcode/{postcode}")
    public ResponseEntity<RestaurantDto> findRestaurantByPostcode(@PathVariable String postcode) {
        RestaurantDto restaurant = restaurantService.findByPostcode(postcode);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @Operation(
            summary = "Find Restaurant By City REST API",
            description = "Find Restaurant By City REST API is used to get restaurants by city from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/by-city/{city}")
    public ResponseEntity<List<RestaurantDto>> findRestaurantByCity(@PathVariable String city) {
        List<RestaurantDto> restaurants = restaurantService.findByCity(city);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @Operation(
            summary = "Find Restaurant By Category REST API",
            description = "Find Restaurant By Category REST API is used to get restaurants by a particular category from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<RestaurantDto>> findRestaurantsByCategory(@PathVariable("id") Long categoryId) {
        List<RestaurantDto> restaurants = restaurantService.findByCategoryId(categoryId);
        return ResponseEntity.ok(restaurants);
    }
}

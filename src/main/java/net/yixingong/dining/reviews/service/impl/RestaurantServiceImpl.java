package net.yixingong.dining.reviews.service.impl;

import net.yixingong.dining.reviews.entity.Category;
import net.yixingong.dining.reviews.mapper.CategoryMapper;
import net.yixingong.dining.reviews.payload.CategoryDto;
import net.yixingong.dining.reviews.payload.RestaurantDto;
import net.yixingong.dining.reviews.exception.ResourceAlreadyExistsException;
import net.yixingong.dining.reviews.exception.ResourceNotFoundException;
import net.yixingong.dining.reviews.exception.RestaurantNotFoundException;
import net.yixingong.dining.reviews.mapper.RestaurantMapper;
import net.yixingong.dining.reviews.entity.Restaurant;
import net.yixingong.dining.reviews.payload.RestaurantResponse;
import net.yixingong.dining.reviews.repository.RestaurantRepository;
import net.yixingong.dining.reviews.service.CategoryService;
import net.yixingong.dining.reviews.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl extends Category implements RestaurantService {
    private RestaurantRepository restaurantRepository;
    private CategoryService categoryService;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, CategoryService categoryService) {
        this.restaurantRepository = restaurantRepository;
        this.categoryService = categoryService;
    }

    @Override
    public RestaurantDto addRestaurant(RestaurantDto restaurantDto) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findByName(restaurantDto.getName());
        if (optionalRestaurant.isPresent()) {
            throw new ResourceAlreadyExistsException("Restaurant already exists");
        }
        try {
            CategoryDto categoryDto = categoryService.getCategoryById(restaurantDto.getCategoryId());
            Category category = CategoryMapper.MAPPER.mapToCategory(categoryDto);
            Restaurant restaurant = RestaurantMapper.MAPPER.mapToRestaurant(restaurantDto);
            restaurant.setCategory(category);
            restaurantRepository.save(restaurant);
            return RestaurantMapper.MAPPER.mapToRestaurantDto(restaurant);
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException("Category", "id", restaurantDto.getCategoryId());
        }
    }

    @Override
    public RestaurantDto getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));
        return RestaurantMapper.MAPPER.mapToRestaurantDto(restaurant);
    }

    @Override
    public RestaurantResponse getAllRestaurants(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);

        List<Restaurant> listOfRestaurants = restaurants.getContent();

        List<RestaurantDto> content = listOfRestaurants.stream()
                .map(RestaurantMapper.MAPPER::mapToRestaurantDto)
                .collect(Collectors.toList());

        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setContent(content);
        restaurantResponse.setPageNo(restaurants.getNumber());
        restaurantResponse.setPageSize(restaurants.getSize());
        restaurantResponse.setTotalElements(restaurants.getTotalElements());
        restaurantResponse.setTotalPages(restaurants.getTotalPages());
        restaurantResponse.setLast(restaurants.isLast());

        return restaurantResponse;
    }

    @Override
    public RestaurantDto updateRestaurant(RestaurantDto restaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurant.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurant.getId())
        );

        try {
            CategoryDto categoryDto = categoryService.getCategoryById(restaurant.getCategoryId());
            Category category = CategoryMapper.MAPPER.mapToCategory(categoryDto);
            existingRestaurant.setName(restaurant.getName());
            existingRestaurant.setPhoneNumber(restaurant.getPhoneNumber());
            existingRestaurant.setWebsite(restaurant.getWebsite());
            existingRestaurant.setPostcode(restaurant.getPostcode());
            existingRestaurant.setCity(restaurant.getCity());
            existingRestaurant.setCategory(category);
            Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);
            RestaurantDto updatedRestaurantDto = RestaurantMapper.MAPPER.mapToRestaurantDto(updatedRestaurant);
            updatedRestaurantDto.setCategoryId(categoryDto.getId());
            return updatedRestaurantDto;
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException("Category", "id", restaurant.getCategoryId());
        }
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    @Override
    public RestaurantDto findByName(String name) {
        Restaurant restaurant = restaurantRepository.findByName(name)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant named " + name + " found."));
        return RestaurantMapper.MAPPER.mapToRestaurantDto(restaurant);
    }

    @Override
    public RestaurantDto findByPostcode(String postcode) {
        Restaurant restaurant = restaurantRepository.findByPostcode(postcode)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with postcode " + postcode + " found."));
        return RestaurantMapper.MAPPER.mapToRestaurantDto(restaurant);
    }

    @Override
    public List<RestaurantDto> findByCity(String city) {
        List<Restaurant> restaurants = restaurantRepository.findByCity(city);
        return restaurants.stream()
                .map(RestaurantMapper.MAPPER::mapToRestaurantDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDto> findByCategoryId(Long categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        List<Restaurant> restaurants = restaurantRepository.findByCategoryId(categoryDto.getId());
        return restaurants.stream()
                .map(RestaurantMapper.MAPPER::mapToRestaurantDto)
                .collect(Collectors.toList());
    }
}
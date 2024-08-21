package net.yixingong.dining.reviews.service;

import net.yixingong.dining.reviews.entity.Restaurant;
import net.yixingong.dining.reviews.exception.ResourceAlreadyExistsException;
import net.yixingong.dining.reviews.mapper.CategoryMapper;
import net.yixingong.dining.reviews.mapper.RestaurantMapper;
import net.yixingong.dining.reviews.payload.CategoryDto;
import net.yixingong.dining.reviews.payload.RestaurantDto;
import net.yixingong.dining.reviews.payload.RestaurantResponse;
import net.yixingong.dining.reviews.repository.RestaurantRepository;
import net.yixingong.dining.reviews.service.impl.CategoryServiceImpl;
import net.yixingong.dining.reviews.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTests {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private CategoryServiceImpl categoryService;
    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private RestaurantResponse restaurantResponse;

    private final Restaurant restaurant = Restaurant.builder()
            .id(1L)
            .name("Marzo")
            .city("Dublin")
            .phoneNumber("851500000")
            .website("www.marzo.com")
            .postcode("D0P799")
            .build();
    private final RestaurantDto restaurantDto = RestaurantDto.builder()
            .id(1L)
            .name("Marzo")
            .city("Dublin")
            .phoneNumber("851500000")
            .website("www.marzo.com")
            .postcode("D0P799")
            .build();
    private final CategoryDto categoryDto = CategoryDto.builder()
            .id(1L)
            .name("Spanish")
            .description("Spanish food")
            .build();

    @DisplayName("JUnit test for addRestaurant method")
    @Test
    public void givenRestaurantObject_whenAddRestaurant_thenReturnRestaurantObject() {
        given(categoryService.getCategoryById(any()))
                .willReturn(categoryDto);
        given(restaurantRepository.findByName(restaurantDto.getName())).willReturn(Optional.empty());
        given(restaurantRepository.save(any(Restaurant.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        RestaurantDto savedRestaurantDto = restaurantService.addRestaurant(restaurantDto);

        assertThat(savedRestaurantDto).isNotNull();
        assertThat(savedRestaurantDto.getName()).isEqualTo("Marzo");
    }

    @DisplayName("JUnit test for addRestaurant method which throws exception")
    @Test
    public void givenExistingRestaurantName_whenAddRestaurant_thenThrowException() {
        given(restaurantRepository.findByName(restaurantDto.getName())).willReturn(Optional.of(restaurant));

        org.junit.jupiter.api.Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> restaurantService.addRestaurant(restaurantDto));

        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @DisplayName("JUnit test for getAllRestaurants method (positive scenario)")
    @Test
    public void givenRestaurantList_whenGetAllRestaurants_thenReturnRestaurantResponse() {
        // given - precondition or setup
        Restaurant restaurant1 = Restaurant.builder()
                .id(2L)
                .name("Vilada")
                .city("Dublin")
                .phoneNumber("851500000")
                .website("www.vilada.com")
                .postcode("D0P799")
                .build();
        List<Restaurant> restaurants = Arrays.asList(restaurant, restaurant1);
        Page<Restaurant> restaurantPage = new PageImpl<>(restaurants);
        given(restaurantRepository.findAll(any(Pageable.class))).willReturn(restaurantPage);

        // when - action or the behavior that we are going to test
        RestaurantResponse result = restaurantService.getAllRestaurants(0, 10, "name", "asc");

        // then - verify the output
        assertThat(result).isNotNull();
        assertThat(result.getContent().get(1).getName()).isEqualTo("Vilada");
        assertThat(result.getPageNo()).isEqualTo(0);
        assertThat(result.getPageSize()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @DisplayName("JUnit test for getAllRestaurants method (negative scenario)")
    @Test
    public void givenEmptyRestaurantList_whenGetAllRestaurants_thenReturnRestaurantResponse() {
        given(restaurantRepository.findAll(any(Pageable.class))).willReturn(Page.empty());

        RestaurantResponse result = restaurantService.getAllRestaurants(0, 10, "name", "asc");

        assertThat(result.getPageNo()).isEqualTo(0);
        assertThat(result.getPageSize()).isEqualTo(0);
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @DisplayName("JUnit test for getRestaurantById method")
    @Test
    public void givenRestaurantId_whenGetRestaurantById_thenReturnRestaurantObject() {
        // given - precondition or setup
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));

        // when - action or the behavior that we are going to test
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurant.getId());

        // then - verify the output
        assertThat(restaurantDto).isNotNull();
        assertThat(restaurantDto.getName()).isEqualTo("Marzo");
    }

    @DisplayName("JUnit test for updateRestaurant method")
    @Test
    public void givenRestaurantObject_whenUpdateRestaurant_thenReturnUpdatedRestaurantObject() {
        given(categoryService.getCategoryById(any())).willReturn(categoryDto);
        given(restaurantRepository.findById(restaurantDto.getId())).willReturn(Optional.of(restaurant));
        given(restaurantRepository.save(restaurant)).willReturn(restaurant);
        RestaurantDto restaurantDto = RestaurantMapper.MAPPER.mapToRestaurantDto(restaurant);
        restaurantDto.setName("Vivi");
        restaurantDto.setCategoryId(categoryDto.getId());

        RestaurantDto updatedRestaurantDto = restaurantService.updateRestaurant(restaurantDto);

        // then - verify the output
        assertThat(updatedRestaurantDto).isNotNull();
        assertThat(updatedRestaurantDto.getName()).isEqualTo("Vivi");
        assertThat(updatedRestaurantDto.getCategoryId()).isEqualTo(categoryDto.getId());
    }

    @DisplayName("JUnit test for deleteRestaurant method")
    @Test
    public void givenRestaurantId_whenDeleteRestaurant_thenNothing() {
        willDoNothing().given(restaurantRepository).deleteById(restaurant.getId());

        restaurantService.deleteRestaurant(restaurant.getId());

        verify(restaurantRepository, times(1)).deleteById(restaurant.getId());
    }
}

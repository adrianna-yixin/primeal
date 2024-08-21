package net.yixingong.dining.reviews.repository;

import net.yixingong.dining.reviews.entity.Category;
import net.yixingong.dining.reviews.entity.Restaurant;
import static org.assertj.core.api.Assertions.assertThat;

import net.yixingong.dining.reviews.mapper.RestaurantMapper;
import net.yixingong.dining.reviews.payload.CategoryDto;
import net.yixingong.dining.reviews.payload.RestaurantDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class RestaurantRepositoryTests {

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        restaurant = Restaurant.builder()
                .name("Marzo")
                .city("Dublin")
                .phoneNumber("851500000")
                .website("www.marzo.com")
                .postcode("D0P799")
                .build();
    }

    @Test
    public void givenRestaurantObject_whenSave_thenReturnRestaurant() {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        assertThat(savedRestaurant).isNotNull();
        assertThat(savedRestaurant.getId()).isGreaterThan(0);
    }

    @Test
    public void givenRestaurant_whenFindById_thenRestaurant() {
        restaurantRepository.save(restaurant);
        Restaurant restaurantDB = restaurantRepository.findById(restaurant.getId()).get();
        assertThat(restaurantDB).isNotNull();
    }

    @Test
    public void givenRestaurantList_whenFindByAll_thenReturnRestaurantList() {
        Restaurant restaurant1 = Restaurant.builder()
                .name("Aciox")
                .city("Sydney")
                .phoneNumber("851500001")
                .website("www.aciox.com")
                .postcode("D0P799")
                .build();
        restaurantRepository.save(restaurant);
        restaurantRepository.save(restaurant1);

        List<Restaurant> restaurantList = restaurantRepository.findAll();

        assertThat(restaurantList).isNotNull();
        assertThat(restaurantList.size()).isEqualTo(2);
    }

    @Test
    public void givenRestaurantObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        restaurantRepository.save(restaurant);

        Restaurant savedRestaurant = restaurantRepository.findById(restaurant.getId()).get();
        savedRestaurant.setPhoneNumber("12345678");
        savedRestaurant.setName("Melano");
        Restaurant updatedRestaurant = restaurantRepository.save(savedRestaurant);

        assertThat(updatedRestaurant.getPhoneNumber()).isEqualTo("12345678");
        assertThat(updatedRestaurant.getName()).isEqualTo("Melano");
    }

    @Test
    public void givenRestaurantObject_whenDelete_thenRemoveRestaurant() {
        restaurantRepository.save(restaurant);

        restaurantRepository.delete(restaurant);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurant.getId());
        assertThat(restaurantOptional).isEmpty();
    }

    @Test
    public void givenName_whenFindByName_thenReturnRestaurantObject() {
        restaurantRepository.save(restaurant);
        String name = "Marzo";

        Optional<Restaurant> restaurantDB = restaurantRepository.findByName(name);

        assertThat(restaurantDB).isNotNull();
    }

    @Test
    public void givenPostcode_whenFindByPostcode_thenReturnRestaurantObject() {
        restaurantRepository.save(restaurant);
        String postcode = "851500000";

        Optional<Restaurant> restaurantDB = restaurantRepository.findByPostcode(postcode);

        assertThat(restaurantDB).isNotNull();
    }

    @Test
    public void givenCity_whenFindByCity_thenReturnRestaurantList() {
        Restaurant restaurant1 = Restaurant.builder()
                .name("Tani")
                .city("Dublin")
                .phoneNumber("851500889")
                .website("www.tany.com")
                .postcode("D0P800")
                .build();
        restaurantRepository.save(restaurant);
        restaurantRepository.save(restaurant1);
        String city = "Dublin";

        List<Restaurant> restaurantList = restaurantRepository.findByCity(city);

        assertThat(restaurantList).isNotNull();
        assertThat(restaurantList.size()).isEqualTo(2);
    }
}

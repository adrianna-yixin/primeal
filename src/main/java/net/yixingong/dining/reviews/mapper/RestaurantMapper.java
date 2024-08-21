package net.yixingong.dining.reviews.mapper;

import net.yixingong.dining.reviews.entity.Category;
import net.yixingong.dining.reviews.payload.RestaurantDto;
import net.yixingong.dining.reviews.entity.Restaurant;
import net.yixingong.dining.reviews.repository.CategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface RestaurantMapper {
    RestaurantMapper MAPPER = Mappers.getMapper(RestaurantMapper.class);
    RestaurantDto mapToRestaurantDto(Restaurant restaurant);
    Restaurant mapToRestaurant(RestaurantDto restaurantDto);
}

@Component
class InheritedCategoryMapper {

    private final CategoryRepository categoryRepository;

    public InheritedCategoryMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> fromId(Long id) {
        return id != null ? categoryRepository.findById(id) : Optional.empty();
    }
}
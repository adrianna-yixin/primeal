package net.yixingong.dining.reviews.mapper;

import net.yixingong.dining.reviews.entity.Category;
import net.yixingong.dining.reviews.payload.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);
    CategoryDto mapToCategoryDto(Category category);
    Category mapToCategory(CategoryDto categoryDto);
}

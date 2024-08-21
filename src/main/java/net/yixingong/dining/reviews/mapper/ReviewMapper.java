package net.yixingong.dining.reviews.mapper;

import net.yixingong.dining.reviews.payload.ReviewDto;
import net.yixingong.dining.reviews.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper MAPPER = Mappers.getMapper(ReviewMapper.class);
    ReviewDto mapToReviewDto(Review review);
    Review mapToReview(ReviewDto dto);
}

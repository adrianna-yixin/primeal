package net.yixingong.dining.reviews.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "CategoryDto Model Information"
)
public class CategoryDto {
    private Long id;
    @Schema(
            description = "Category Name"
    )
    private String name;
    @Schema(
            description = "Category Description"
    )
    private String description;
}

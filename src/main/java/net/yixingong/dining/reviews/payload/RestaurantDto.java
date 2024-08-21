package net.yixingong.dining.reviews.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "RestaurantDto Model Information"
)
public class RestaurantDto {
    private Long id;
    @NotEmpty(message = "Restaurant name cannot be null or empty")
    private String name;
    @NotEmpty(message = "City cannot be null or empty.")
    private String city;
    @NotEmpty(message = "Restaurant postcode cannot be null or empty.")
    private String postcode;
    private String phoneNumber;
    private String website;
    private Long categoryId;
}

package net.yixingong.dining.reviews.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "ReviewDto Model Information"
)
public class ReviewDto {
    private Long id;
    @NotEmpty(message = "Username is required")
    private String username;
    @Email(message = "Email address should be valid.")
    @NotEmpty(message = "Email address is required")
    private String email;
    @NotEmpty(message = "Review content cannot be null or empty.")
    private String content;
    @NotNull(message = "Rating cannot be null.")
    private Integer rating;
}

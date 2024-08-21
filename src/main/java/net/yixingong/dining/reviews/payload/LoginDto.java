package net.yixingong.dining.reviews.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "LoginDto Model Information"
)
public class LoginDto {
    @NotEmpty(message = "Username or email is required")
    private String usernameOrEmail;
    @NotEmpty(message = "Password is required")
    private String password;
}
package com.kuroko.heathyapi.feature.account.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "Email may not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Invalid email")
    private String email;
    @NotEmpty(message = "Password may not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Size(max = 20, message = "Password must not exceed 24 characters")
    private String password;
}

package com.kuroko.heathyapi.feature.account.payload;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Name may not be blank")
    private String name;
    @NotEmpty(message = "Email may not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Invalid email")
    private String email;
    @NotEmpty(message = "Password may not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Size(max = 20, message = "Password must not exceed 24 characters")
    private String password;
    @NotBlank(message = "Goal may not be blank")
    private String goal;
    @NotBlank(message = "Gender may not be blank")
    private String gender;
    @NotNull(message = "Age may not be blank")
    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 200, message = "Age must be less than 200")
    private int age;
    @NotNull(message = "Height may not be blank")
    private double height;
    @NotNull(message = "Weight may not be blank")
    private double weight;
    @NotNull(message = "Activity may not be blank")
    private double coefficientOfActivity;
}

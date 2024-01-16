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
    // @Pattern(regexp =
    // "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
    // message = "Invalid password")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Size(max = 20, message = "Password must not exceed 24 characters")
    private String password;
    @NotBlank(message = "Goal may not be blank")
    private String goal;
    @NotBlank(message = "Gender may not be blank")
    private String gender;
    @NotBlank(message = "Age may not be blank")
    private int age;
    @NotBlank(message = "Height may not be blank")
    private double height;
    @NotBlank(message = "Weight may not be blank")
    private double weight;
    @NotBlank(message = "Activity may not be blank")
    private double coefficientOfActivity;
}

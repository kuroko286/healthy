package com.kuroko.heathyapi.feature.account.payload;

import java.util.List;

import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.user.payload.UserResponse;
import com.kuroko.heathyapi.feature.water.Water;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserResponse userResponse;

    public AuthResponse(String token, User user, List<Meal> meals, List<Water> water) {
        this.token = token;
        this.userResponse = new UserResponse(user, meals, water);
    }
}

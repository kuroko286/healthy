package com.kuroko.heathyapi.feature.account.payload;

import java.util.List;

import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.meal.Meal;
import com.kuroko.heathyapi.feature.user.User;
import com.kuroko.heathyapi.feature.user.payload.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserDto user;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = new UserDto(user, user.getAccount());
    }
}

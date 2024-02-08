package com.kuroko.heathyapi.feature.account.payload;

import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.user.payload.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserDto userDto;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.userDto = new UserDto(user, user.getAccount());
    }
}

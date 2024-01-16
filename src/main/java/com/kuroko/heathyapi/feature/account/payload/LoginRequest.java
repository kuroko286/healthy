package com.kuroko.heathyapi.feature.account.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

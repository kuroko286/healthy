package com.kuroko.heathyapi.feature.account.service;

import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
import com.kuroko.heathyapi.feature.account.payload.LoginRequest;
import com.kuroko.heathyapi.feature.account.payload.RegisterRequest;

public interface AccountService {
    AuthResponse createAccount(RegisterRequest registerRequest);

    AuthResponse authenticate(LoginRequest loginRequest);

    void updatePassword(String email, String password);

    void forgotPassword(String email);

    void resetPassword(String token, String password);

    void logout(String token);
}

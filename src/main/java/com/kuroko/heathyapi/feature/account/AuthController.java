package com.kuroko.heathyapi.feature.account;

import org.springframework.web.bind.annotation.RestController;

import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
import com.kuroko.heathyapi.feature.account.payload.LoginRequest;
import com.kuroko.heathyapi.feature.account.payload.RegisterRequest;
import com.kuroko.heathyapi.feature.account.service.IAccountService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class AuthController {
    @Autowired
    private IAccountService accountService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok().body(accountService.authenticate(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(registerRequest));
    }

    @PostMapping("/signout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok().body("User logged out successfully");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        accountService.forgotPassword(email);
        return ResponseEntity.ok().body("Password reset link sent to your email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody String password) {
        accountService.resetPassword(token, password);
        return ResponseEntity.ok().body("Password reset successfully");
    }
}

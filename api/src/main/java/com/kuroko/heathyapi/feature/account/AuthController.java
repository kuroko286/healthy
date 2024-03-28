package com.kuroko.heathyapi.feature.account;

import org.springframework.web.bind.annotation.RestController;

import com.kuroko.heathyapi.enums.CookieName;
import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
import com.kuroko.heathyapi.feature.account.payload.LoginRequest;
import com.kuroko.heathyapi.feature.account.payload.RegisterRequest;
import com.kuroko.heathyapi.feature.account.service.AccountService;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    private AccountService accountService;

    @Value("${app.jwt-expiration-milliseconds}")
    private long accessTokenCookieTTL;
    private final long userIdCookieTTL = 100 * 365 * 24 * 60 * 60; // 100 years
    private final String ACCESS_TOKEN = "access_token";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        AuthResponse authResponse = accountService.authenticate(loginRequest);
        HttpHeaders headers = createHeadersFromAuthResponse(authResponse);
        return ResponseEntity.ok().headers(headers)
                .body(String.valueOf(authResponse.getUserResponse().getUserId())); // send client userId, not account id
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest registerRequest) {
        AuthResponse authResponse = accountService.createAccount(registerRequest);
        HttpHeaders headers = createHeadersFromAuthResponse(authResponse);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers)
                .body(String.valueOf(authResponse.getUserResponse().getUserId())); // send client userId, not account id
    }

    public HttpHeaders createHeadersFromAuthResponse(AuthResponse authResponse) {

        ResponseCookie tokenCookie = ResponseCookie.from(CookieName.ACCESS_TOKEN.toString(), authResponse.getToken())
                .maxAge(accessTokenCookieTTL / 1000) // in seconds
                .path("/").httpOnly(true).build();
        ResponseCookie idCookie = ResponseCookie
                .from(CookieName.USER_ID.toString(), String.valueOf(authResponse.getUserResponse().getUserId()))
                .maxAge(userIdCookieTTL)
                .path("/").build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, tokenCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, idCookie.toString());
        return headers;
    }

    @PostMapping("/signout")
    public ResponseEntity<String> logout(@CookieValue(name = ACCESS_TOKEN) String token) {
        accountService.logout(token);
        ResponseCookie cookie = ResponseCookie.from(CookieName.ACCESS_TOKEN.toString(), "").maxAge(0)
                .path("/").build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().headers(headers).body("User logged out successfully");
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

// package com.kuroko.heathyapi.feature.account;

// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.*;

// import org.junit.jupiter.api.BeforeEach;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
// import com.kuroko.heathyapi.feature.account.payload.LoginRequest;
// import com.kuroko.heathyapi.feature.account.payload.RegisterRequest;
// import com.kuroko.heathyapi.feature.account.service.AccountService;

// public class AuthControllerTest {

// private AuthController authController;
// private AccountService accountService;

// @BeforeEach
// void setUp() {
// accountService = mock(AccountService.class);
// authController = new AuthController(accountService);
// }

// @Test
// void testLogin() {
// LoginRequest loginRequest = new LoginRequest("username", "password");
// AuthResponse authResponse = new AuthResponse("token", 1L);
// when(accountService.authenticate(loginRequest)).thenReturn(authResponse);

// ResponseEntity<String> response = authController.login(loginRequest);

// assertEquals(HttpStatus.OK, response.getStatusCode());
// assertEquals("1", response.getBody());
// }

// @Test
// void testRegister() {
// RegisterRequest registerRequest = new RegisterRequest("username", "password",
// "email");
// AuthResponse authResponse = new AuthResponse("token", 2L);
// when(accountService.createAccount(registerRequest)).thenReturn(authResponse);

// ResponseEntity<String> response = authController.register(registerRequest);

// assertEquals(HttpStatus.CREATED, response.getStatusCode());
// assertEquals("2", response.getBody());
// }

// // Add more test methods for other endpoints if needed
// @Test
// void testLogout() {
// String token = "sampleToken";
// ResponseEntity<String> response = authController.logout(token);

// assertEquals(HttpStatus.OK, response.getStatusCode());
// assertEquals("User logged out successfully", response.getBody());
// }

// }

package com.kuroko.heathyapi.feature.account.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.components.ModelMapper;
import com.kuroko.heathyapi.enums.TokenState;
import com.kuroko.heathyapi.exception.business.ResourceAlreadyExistsException;
import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.exception.security.AuthenticationException;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
import com.kuroko.heathyapi.feature.account.payload.LoginRequest;
import com.kuroko.heathyapi.feature.account.payload.RegisterRequest;
import com.kuroko.heathyapi.feature.meal.MealRepository;
import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.user.model.CustomUserDetails;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.water.Water;
import com.kuroko.heathyapi.feature.water.WaterRepository;
import com.kuroko.heathyapi.feature.weight.Weight;
import com.kuroko.heathyapi.service.JwtService;
import com.kuroko.heathyapi.service.RedisService;
import com.kuroko.heathyapi.service.email.EmailService;

@Service
public class AccountServiceImpl implements AccountService {

        @Autowired
        private AccountRepository accountRepository;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private JwtService jwtService;
        @Autowired
        private AuthenticationManager authenticationManager;
        @Autowired
        private EmailService emailService;
        @Autowired
        private ModelMapper modelMapper;
        @Autowired
        private WaterRepository waterRepository;
        @Autowired
        private MealRepository mealRepository;
        @Autowired
        private RedisService redisService;

        @Value("${app.frontend.url}")
        private String frontendUrl;
        @Value("${app.jwt-expiration-milliseconds}")
        private Long jwtExpirationMs;

        @Override
        public AuthResponse createAccount(RegisterRequest registerRequest) {
                if (accountRepository.existsByEmail(registerRequest.getEmail())) {
                        throw new ResourceAlreadyExistsException(
                                        "Account with email " + registerRequest.getEmail() + " already exists");
                }

                Weight weight = modelMapper.maptoWeight(registerRequest);
                User user = modelMapper.mapToUser(registerRequest);
                Account account = modelMapper.mapToAccount(registerRequest);

                user.getWeights().add(weight);
                account.setUser(user);
                user.setAccount(account);

                Account savedAccount = accountRepository.save(account);

                User savedUser = savedAccount.getUser();
                List<Meal> meals = mealRepository.findByUserAndTimeRange(user, LocalDate.now().atStartOfDay(),
                                LocalDate.now().atStartOfDay().plusDays(1));
                List<Water> water = waterRepository.findByUserAndTimeRange(user, LocalDate.now().atStartOfDay(),
                                LocalDate.now().atStartOfDay().plusDays(1));
                // System.out.println(savedAccount);
                AuthResponse authResponse = new AuthResponse(
                                jwtService.generateToken(new CustomUserDetails(savedAccount)), savedUser, meals, water);
                return authResponse;
        }

        @Override
        public AuthResponse authenticate(LoginRequest loginRequest) {
                Account account = accountRepository.findByEmail(loginRequest.getEmail())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Account with email " + loginRequest.getEmail() + " not found."));
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                loginRequest.getPassword()));
                User user = account.getUser();
                List<Meal> meals = mealRepository.findByUserAndTimeRange(user, LocalDate.now().atStartOfDay(),
                                LocalDate.now().atStartOfDay().plusDays(1));
                List<Water> water = waterRepository.findByUserAndTimeRange(user, LocalDate.now().atStartOfDay(),
                                LocalDate.now().atStartOfDay().plusDays(1));
                String jwtToken = jwtService.generateToken(new CustomUserDetails(account));
                AuthResponse authResponse = new AuthResponse(jwtToken, user, meals, water);

                return authResponse;
        }

        @Override
        public void updatePassword(String email, String password) {
                Account account = accountRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                                "Account with email " + email + " not found."));
                account.setPassword(passwordEncoder.encode(password));
                accountRepository.save(account);
        }

        @Override
        public void forgotPassword(String email) {
                Account account = accountRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                                "Account with email " + email + " not found."));
                String token = jwtService.generateToken(new CustomUserDetails(account));
                String link = getResetPasswordLink(token);

                emailService.sendEmail(email, "Reset password", "Click here to reset your password: " + link);
        }

        public String getResetPasswordLink(String token) {
                return frontendUrl + "/reset-password?token=" + token;
        }

        @Override
        public void resetPassword(String token, String password) {
                String email = jwtService.extractUsername(token);
                Account account = accountRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                                "Account with email " + email + " not found."));
                if (!jwtService.isTokenValid(token)) {
                        throw new AuthenticationException("Invalid token");
                }
                account.setPassword(passwordEncoder.encode(password));
                accountRepository.save(account);
        }

        @Override
        public void logout(String token) {
                redisService.deleteValue(token);
        }
}

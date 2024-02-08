package com.kuroko.heathyapi.feature.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.components.CustomUserDetails;
import com.kuroko.heathyapi.components.ModelMapper;
import com.kuroko.heathyapi.exception.business.ResourceAlreadyExistsException;
import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.exception.security.AuthenticationException;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
import com.kuroko.heathyapi.feature.account.payload.LoginRequest;
import com.kuroko.heathyapi.feature.account.payload.RegisterRequest;
import com.kuroko.heathyapi.feature.email.EmailService;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.weight.Weight;
import com.kuroko.heathyapi.service.JwtService;

@Service
public class AccountService implements IAccountService {

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

    @Value("${app.frontend.url}")
    private String frontendUrl;

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
        Account savedAccount = accountRepository.save(account);

        AuthResponse authResponse = new AuthResponse(
                jwtService.generateToken(new CustomUserDetails(savedAccount)), user);
        return authResponse;
    }

    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
        Account account = accountRepository.findByEmail(loginRequest.getEmail()).get();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        User user = account.getUser();
        String jwtToken = jwtService.generateToken(new CustomUserDetails(account));
        AuthResponse authResponse = new AuthResponse(jwtToken, user);

        return authResponse;
    }

    @Override
    public void updatePassword(String email, String password) {
        Account account = accountRepository.findByEmail(email).get();
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
    }

    @Override
    public void forgotPassword(String email) {
        Account account = accountRepository.findByEmail(email).get();
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
        UserDetails userDetails = new CustomUserDetails(account);
        if (!jwtService.isTokenValid(token, userDetails)) {
            throw new AuthenticationException("Invalid token");
        }
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
    }

}

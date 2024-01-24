package com.kuroko.heathyapi.feature.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceAlreadyExistsException;
import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.exception.security.AuthenticationException;
import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
import com.kuroko.heathyapi.feature.account.payload.LoginRequest;
import com.kuroko.heathyapi.feature.account.payload.RegisterRequest;
import com.kuroko.heathyapi.feature.user.User;
import com.kuroko.heathyapi.feature.user.UserRepository;
import com.kuroko.heathyapi.feature.weight.Weight;
import com.kuroko.heathyapi.feature.weight.WeightRepository;
import com.kuroko.heathyapi.service.JwtService;

@Service
public class AccountService implements IAccountService {

    @Value("${app.frontend.url}")
    private String frontendUrl;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WeightRepository weightRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account updateAccount(Long id, Account account) {
        if (accountRepository.existsById(id)) {
            account.setId(id);
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("Account with id " + id + " not found");
        }
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public AuthResponse createAccount(RegisterRequest registerRequest) {
        if (accountRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    "Account with email " + registerRequest.getEmail() + " already exists");
        }
        Account account = new Account();
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setEmail(registerRequest.getEmail());
        account.setRole(Role.USER);
        accountRepository.save(account);

        User user = new User();
        user.setName(registerRequest.getName());
        user.setGoal(registerRequest.getGoal());
        user.setGender(registerRequest.getGender());
        user.setHeight(registerRequest.getHeight());
        user.setWeight(registerRequest.getWeight());
        user.setCoefficientOfActivity(registerRequest.getCoefficientOfActivity());
        user.setAccount(account);
        userRepository.save(user);

        Weight weight = new Weight();
        weight.setWeight(registerRequest.getWeight());
        weight.setUser(user);
        weightRepository.save(weight);

        AuthResponse authResponse = new AuthResponse(
                jwtService.generateToken(new CustomUserDetails(account)), user);
        return authResponse;
    }

    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
        Account account = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account with email " + loginRequest.getEmail() + " not found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        User user = account.getUser();
        String jwtToken = jwtService.generateToken(new CustomUserDetails(account));
        AuthResponse authResponse = new AuthResponse(jwtToken, user);

        return authResponse;
    }

    @Override
    public void updatePassword(String email, String password) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                "Account with email " + email + " not found"));
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
    }

    @Override
    public void forgotPassword(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                "Account with email " + email + " not found"));
        String token = jwtService.generateToken(new CustomUserDetails(account));
        String link = frontendUrl + "/reset-password?token=" + token;

        emailService.sendEmail(email, "Reset password", "Click here to reset your password: " + link);
    }

    @Override
    public void resetPassword(String token, String password) {
        String email = jwtService.extractUsername(token);
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                "Account with email " + email + " not found"));
        UserDetails userDetails = new CustomUserDetails(account);
        if (!jwtService.isTokenValid(token, userDetails)) {
            throw new AuthenticationException("Invalid token");
        }
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
    }

}

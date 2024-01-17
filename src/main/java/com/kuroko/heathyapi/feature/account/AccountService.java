package com.kuroko.heathyapi.feature.account;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
import com.kuroko.heathyapi.feature.account.payload.LoginRequest;
import com.kuroko.heathyapi.feature.account.payload.RegisterRequest;
import com.kuroko.heathyapi.feature.meal.MealRepository;
import com.kuroko.heathyapi.feature.user.User;
import com.kuroko.heathyapi.feature.user.UserRepository;
import com.kuroko.heathyapi.feature.water.WaterRepository;
import com.kuroko.heathyapi.service.JwtService;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private WaterRepository waterRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

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
        Account account = new Account();
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setEmail(registerRequest.getEmail());
        account.setRole(Role.USER);

        User user = new User();
        user.setName(registerRequest.getName());
        user.setGoal(registerRequest.getGoal());
        user.setGender(registerRequest.getGender());
        user.setHeight(registerRequest.getHeight());
        user.setWeight(registerRequest.getWeight());
        user.setCoefficientOfActivity(registerRequest.getCoefficientOfActivity());
        user.setAccount(account);

        userRepository.save(user);
        accountRepository.save(account);
        AuthResponse authResponse = new AuthResponse(
                jwtService.generateToken(new CustomUserDetails(account)), user);
        authResponse.setConsumedMealsByDay(mealRepository.findByUserAndCreatedAt(user, new Date()));
        authResponse.setConsumedWaterByDay(waterRepository.findByUserAndCreatedAt(user, new Date()));
        return authResponse;
    }

    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        Account account = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account with email " + loginRequest.getEmail() + " not found"));
        User user = account.getUser();
        String jwtToken = jwtService.generateToken(new CustomUserDetails(account));
        AuthResponse authResponse = new AuthResponse(jwtToken, user);
        authResponse.setConsumedMealsByDay(mealRepository.findByUserAndCreatedAt(user, new Date()));
        authResponse.setConsumedWaterByDay(waterRepository.findByUserAndCreatedAt(user, new Date()));
        return authResponse;
    }

    @Override
    public void updatePassword(Long id, String password) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Account with id " + id + " not found"));
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
    }

}

package com.kuroko.heathyapi.feature.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
import com.kuroko.heathyapi.feature.user.payload.StatisticsDto;
import com.kuroko.heathyapi.feature.user.payload.UserReq;
import com.kuroko.heathyapi.feature.user.payload.WeightDto;
import com.kuroko.heathyapi.service.JwtService;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtService jwtService;

    @Override
    public StatisticsDto getStatistics(int month, String token) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AuthResponse getCurrentUser(String token) {
        String email = jwtService.extractUsername(token);
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        return new AuthResponse(token, account.getUser());
    }

    @Override
    public void updateUserInfo(String token, UserReq userReq) {
        String email = jwtService.extractUsername(token);
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();
        // TODO: update user here

    }

    @Override
    public void updateUserGoal(String token, String goal) {
        String email = jwtService.extractUsername(token);
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();
        // TODO: update user goal here
        user.setGoal(goal);
        userRepository.save(user);
    }

    @Override
    public void addUserWeight(String token, WeightDto weightDto) {
        String email = jwtService.extractUsername(token);
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();
        // TODO: Implement add user weight

    }

}

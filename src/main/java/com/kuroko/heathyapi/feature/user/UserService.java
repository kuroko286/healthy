package com.kuroko.heathyapi.feature.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.components.Nutrition;
import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.user.payload.Goal;
import com.kuroko.heathyapi.feature.user.payload.GoalUpdatedDto;
import com.kuroko.heathyapi.feature.user.payload.StatisticsDto;
import com.kuroko.heathyapi.feature.user.payload.UserDto;
import com.kuroko.heathyapi.feature.user.payload.UserReq;
import com.kuroko.heathyapi.service.JwtService;
import com.kuroko.heathyapi.util.Patcher;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private Patcher patcher;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StatisticsDto getStatistics(int month, String token) {
        String email = jwtService.extractUsername(token);
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();

        return null;
    }

    @Override
    public UserDto getCurrentUser(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        return new UserDto(account.getUser(), account);
    }

    @Override
    public UserDto updateUserInfo(String email, UserReq userReq) {
        try {
            Account account = accountRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
            User user = account.getUser();
            patcher.userPatcher(user, mapToUser(userReq));
            userRepository.save(user);
            return new UserDto(user, account);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public GoalUpdatedDto updateUserGoal(String email, Goal goal) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();
        user.setGoal(goal.getGoal());
        userRepository.save(user);
        return new GoalUpdatedDto(goal.getGoal(), new Nutrition(user));
    }

    public User mapToUser(UserReq userReq) {
        return modelMapper.map(userReq, User.class);
    }

}

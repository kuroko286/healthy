package com.kuroko.heathyapi.feature.user;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kuroko.heathyapi.components.Nutrition;
import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.food.CaloriesPD;
import com.kuroko.heathyapi.feature.food.FoodRepository;
import com.kuroko.heathyapi.feature.user.payload.Goal;
import com.kuroko.heathyapi.feature.user.payload.GoalUpdatedDto;
import com.kuroko.heathyapi.feature.user.payload.StatisticsDto;
import com.kuroko.heathyapi.feature.user.payload.UserDto;
import com.kuroko.heathyapi.feature.user.payload.UserReq;
import com.kuroko.heathyapi.feature.water.WaterPD;
import com.kuroko.heathyapi.feature.water.WaterRepository;
import com.kuroko.heathyapi.feature.weight.WeightPD;
import com.kuroko.heathyapi.feature.weight.WeightRepository;
import com.kuroko.heathyapi.service.FileUploadService;
import com.kuroko.heathyapi.service.JwtService;
import com.kuroko.heathyapi.util.Patcher;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private Patcher patcher;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WaterRepository waterRepository;
    @Autowired
    private WeightRepository weightRepository;
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public StatisticsDto getStatistics(int month, String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();
        LocalDate today = LocalDate.now();
        if (today.getMonthValue() == month) { // get current month
            return getStatisticsMonth(user, today.getMonthValue(), today.getYear());
        }
        today = today.minusMonths(1);
        if (today.getMonthValue() == month) { // get last month
            return getStatisticsMonth(user, today.getMonthValue(), today.getYear());
        } else {
            throw new InvalidParameterException("Only support current or previous month");
        }
    }

    public StatisticsDto getStatisticsMonth(User user, int month, int year) {

        List<Object[]> waterPerDay = waterRepository.findByYearAndMonthAndUser(year, month, user);
        List<WaterPD> waterPerDayList = waterPerDay.stream()
                .map(water -> new WaterPD((int) water[0], (Double) water[1])).collect(Collectors.toList());

        List<Object[]> weightPerDay = weightRepository.findByYearAndMonthAndUser(year, month, user);
        List<WeightPD> weightPerDayList = weightPerDay.stream()
                .map(weight -> new WeightPD((int) weight[0], (Double) weight[1])).collect(Collectors.toList());

        List<Object[]> callPerDay = foodRepository.findByYearAndMonthAndUser(year, month, user);
        List<CaloriesPD> callPerDayList = callPerDay.stream()
                .map(call -> new CaloriesPD((int) call[0], (Double) call[1])).collect(Collectors.toList());
        return new StatisticsDto(callPerDayList, waterPerDayList, weightPerDayList);
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

    @Override
    public void updateUserAvatar(String email, MultipartFile avatar) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                "Account with email " + email + " not found."));
        User user = account.getUser();
        String url = fileUploadService.uploadFile(avatar);
        user.setAvatarURL(url);
        userRepository.save(user);
    }

}

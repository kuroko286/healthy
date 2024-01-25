package com.kuroko.heathyapi.feature.user;

import org.springframework.web.multipart.MultipartFile;

import com.kuroko.heathyapi.feature.user.payload.Goal;
import com.kuroko.heathyapi.feature.user.payload.GoalUpdatedDto;
import com.kuroko.heathyapi.feature.user.payload.StatisticsDto;
import com.kuroko.heathyapi.feature.user.payload.UserDto;
import com.kuroko.heathyapi.feature.user.payload.UserReq;

public interface IUserService {

    StatisticsDto getStatistics(int month, String email);

    UserDto getCurrentUser(String email);

    UserDto updateUserInfo(String email, UserReq userReq);

    GoalUpdatedDto updateUserGoal(String email, Goal goal);

    void updateUserAvatar(String email, MultipartFile avatar);

}

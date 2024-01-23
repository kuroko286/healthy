package com.kuroko.heathyapi.feature.user;

import com.kuroko.heathyapi.feature.user.payload.Goal;
import com.kuroko.heathyapi.feature.user.payload.GoalUpdatedDto;
import com.kuroko.heathyapi.feature.user.payload.StatisticsDto;
import com.kuroko.heathyapi.feature.user.payload.UserDto;
import com.kuroko.heathyapi.feature.user.payload.UserReq;
import com.kuroko.heathyapi.feature.weight.WeightDto;

public interface IUserService {

    StatisticsDto getStatistics(int month, String token);

    UserDto getCurrentUser(String email);

    UserDto updateUserInfo(String email, UserReq userReq);

    GoalUpdatedDto updateUserGoal(String email, Goal goal);

}

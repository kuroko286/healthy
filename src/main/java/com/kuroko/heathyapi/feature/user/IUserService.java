package com.kuroko.heathyapi.feature.user;

import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
import com.kuroko.heathyapi.feature.user.payload.StatisticsDto;
import com.kuroko.heathyapi.feature.user.payload.UserReq;
import com.kuroko.heathyapi.feature.user.payload.WeightDto;

public interface IUserService {

    StatisticsDto getStatistics(int month, String token);

    AuthResponse getCurrentUser(String token);

    void updateUserInfo(String token, UserReq userReq);

    void updateUserGoal(String token, String goal);

    void addUserWeight(String token, WeightDto weightDto);

}

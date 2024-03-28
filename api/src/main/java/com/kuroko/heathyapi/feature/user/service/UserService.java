package com.kuroko.heathyapi.feature.user.service;

import org.springframework.web.multipart.MultipartFile;

import com.kuroko.heathyapi.feature.user.payload.GoalRequest;
import com.kuroko.heathyapi.feature.user.payload.GoalResponse;
import com.kuroko.heathyapi.feature.user.payload.StatisticsResponse;
import com.kuroko.heathyapi.feature.user.payload.UserResponse;
import com.kuroko.heathyapi.feature.user.payload.UserRequest;

public interface UserService {

    StatisticsResponse getStatistics(int month, int year, Long id);

    UserResponse getCurrentUser(Long id);

    UserResponse updateUserInfo(Long id, UserRequest userReq);

    GoalResponse updateUserGoal(Long id, GoalRequest goal);

    void updateUserAvatar(Long id, MultipartFile avatar);

}

package com.kuroko.heathyapi.feature.water;

import java.util.Date;
import java.util.List;

import com.kuroko.heathyapi.feature.user.User;

public interface IWaterService {
    List<Water> getWaterByUserAndCreatedAt(User user, Date date);

    void addWaterIntake(String token, WaterDto waterDto);

    void deleteWaterIntake(String token, WaterDto waterDto);
}

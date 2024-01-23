package com.kuroko.heathyapi.feature.water;

import java.util.Date;
import java.util.List;

import com.kuroko.heathyapi.feature.user.User;

public interface IWaterService {
    List<Water> getWaterByUserAndCreatedAt(User user, Date date);

    WaterDto addWaterIntake(String email, WaterDto waterDto);

    WaterDto deleteWaterIntake(String email);
}

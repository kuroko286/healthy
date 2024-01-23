package com.kuroko.heathyapi.feature.weight;

import com.kuroko.heathyapi.feature.components.Nutrition;
import com.kuroko.heathyapi.feature.user.User;
import com.kuroko.heathyapi.util.UserUtil;

import lombok.Data;

@Data
public class WeightUpdatedDto {
    private double weight;
    private Nutrition dailyNutrition;
    private double dailyCalories;
    private double dailyWater;

    public WeightUpdatedDto(double weight, User user) {
        this.weight = weight;
        this.dailyNutrition = new Nutrition(user);
        this.dailyCalories = UserUtil.caculateDailyCalories(user);
        this.dailyWater = UserUtil.caculateDailyWater(user);

    }
}

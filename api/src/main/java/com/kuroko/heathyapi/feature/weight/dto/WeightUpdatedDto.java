package com.kuroko.heathyapi.feature.weight.dto;

import com.kuroko.heathyapi.components.Nutrition;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.util.NutritionCaculator;

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
        this.dailyCalories = NutritionCaculator.caculateDailyCalories(user);
        this.dailyWater = NutritionCaculator.caculateDailyWater(user);
    }
}

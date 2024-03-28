package com.kuroko.heathyapi.feature.user.payload;

import com.kuroko.heathyapi.components.Nutrition;

import lombok.Data;

@Data
public class GoalResponse {
    private String goal;
    private Nutrition dailyNutrition;

    public GoalResponse(String goal, Nutrition dailyNutrition) {
        this.goal = goal;
        this.dailyNutrition = dailyNutrition;
    }
}

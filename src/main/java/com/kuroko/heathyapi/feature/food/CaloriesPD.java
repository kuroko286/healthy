package com.kuroko.heathyapi.feature.food;

import lombok.Data;

@Data
public class CaloriesPD { // PD: Per Day
    private int day;
    private double calories;

    public CaloriesPD(int day, double calories) {
        this.day = day;
        this.calories = calories;
    }
}

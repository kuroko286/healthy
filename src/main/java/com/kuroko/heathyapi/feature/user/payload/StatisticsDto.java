package com.kuroko.heathyapi.feature.user.payload;

import java.util.List;

import lombok.Data;

@Data
public class StatisticsDto {
    private List<CaloriesPD> callPerDay; // callories per day
    private List<WaterPD> waterPerDay;
    private List<WeightPD> weightPerDay;
    private double avgCalories;
    private double avgWater;
    private double avgWeight;
}

@Data
class CaloriesPD { // PD: Per Day
    private int day;
    private double calories;
}

@Data
class WaterPD {
    private int day;
    private double ml;
}

@Data
class WeightPD {
    private int day;
    private double weight;
}

package com.kuroko.heathyapi.feature.user.payload;

import java.util.List;

import com.kuroko.heathyapi.feature.food.CaloriesPD;
import com.kuroko.heathyapi.feature.water.WaterPD;
import com.kuroko.heathyapi.feature.weight.WeightPD;

import lombok.Data;

@Data
public class StatisticsDto {
    private List<Object[]> callPerDay; // callories per day
    private List<Object[]> waterPerDay;
    private List<Object[]> weightPerDay;
    private double avgCalories;
    private double avgWater;
    private double avgWeight;

    public StatisticsDto(List<Object[]> callPerDay, List<Object[]> waterPerDay, List<Object[]> weightPerDay) {
        this.callPerDay = callPerDay;
        this.waterPerDay = waterPerDay;
        this.weightPerDay = weightPerDay;
        // this.avgCalories =
        // callPerDay.stream().mapToDouble(CaloriesPD::getCalories).average().orElse(0);
        // this.avgWater =
        // waterPerDay.stream().mapToDouble(WaterPD::getMl).average().orElse(0);
        // this.avgWeight =
        // weightPerDay.stream().mapToDouble(WeightPD::getWeight).average().orElse(0);
        this.avgCalories = 0;
        this.avgWater = 0;
        this.avgWeight = 0;
    }

    public StatisticsDto() {

    }
}

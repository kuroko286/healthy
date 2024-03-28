package com.kuroko.heathyapi.feature.user.payload;

import java.io.Serializable;
import java.util.List;

import com.kuroko.heathyapi.feature.food.dto.CaloriesPD;
import com.kuroko.heathyapi.feature.water.dto.WaterPD;
import com.kuroko.heathyapi.feature.weight.dto.WeightPD;

import lombok.Data;

@Data
public class StatisticsResponse implements Serializable {
    private List<CaloriesPD> callPerDay; // callories per day
    private List<WaterPD> waterPerDay;
    private List<WeightPD> weightPerDay;
    private double avgCalories;
    private double avgWater;
    private double avgWeight;

    public StatisticsResponse(List<CaloriesPD> callPerDayList, List<WaterPD> waterPerDayList,
            List<WeightPD> weightPerDayList) {
        this.callPerDay = callPerDayList;
        this.waterPerDay = waterPerDayList;
        this.weightPerDay = weightPerDayList;
        this.avgCalories = callPerDay.stream().mapToDouble(CaloriesPD::getCalories).average().orElse(0);
        this.avgWater = waterPerDay.stream().mapToDouble(WaterPD::getMl).average().orElse(0);
        this.avgWeight = weightPerDay.stream().mapToDouble(WeightPD::getWeight).average().orElse(0);
    }

    public StatisticsResponse() {

    }
}

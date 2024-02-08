package com.kuroko.heathyapi.feature.weight.dto;

import lombok.Data;

@Data
public class WeightPD { // PD = per day (dto for monthly statistics)
    private int day;
    private double weight;

    public WeightPD(int day, double weight) {
        this.day = day;
        this.weight = weight;
    }
}

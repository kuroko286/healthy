package com.kuroko.heathyapi.feature.weight;

import lombok.Data;

@Data
public class WeightPD {
    private int day;
    private double weight;

    public WeightPD(int day, double weight) {
        this.day = day;
        this.weight = weight;
    }
}

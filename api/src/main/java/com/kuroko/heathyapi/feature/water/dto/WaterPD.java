package com.kuroko.heathyapi.feature.water.dto;

import lombok.Data;

@Data
public class WaterPD {
    private int day;
    private double ml;

    public WaterPD(int day, double ml) {
        this.day = day;
        this.ml = ml;
    }
}

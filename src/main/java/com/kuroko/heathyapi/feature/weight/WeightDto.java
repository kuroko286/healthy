package com.kuroko.heathyapi.feature.weight;

import lombok.Data;

@Data
public class WeightDto {
    private double weight;

    public WeightDto(double weight) {
        this.weight = weight;
    }

    public WeightDto() {

    }
}

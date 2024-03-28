package com.kuroko.heathyapi.feature.water.dto;

import java.time.LocalDate;
import java.util.List;

import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.water.Water;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterDto {
    private double ml; // water intake in milis

    public WaterDto(List<Water> water) {
        double ml = water.stream().mapToDouble(Water::getAmount).sum();
        this.ml = ml;
    }
}

package com.kuroko.heathyapi.feature.water;

import java.time.LocalDate;
import java.util.List;

import com.kuroko.heathyapi.feature.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterDto {
    private double ml; // water intake in milis

    public WaterDto(List<Water> water) {
        this.ml = water.stream().mapToDouble(Water::getAmount).sum();
    }

    public WaterDto(User user) {
        // get water of the current day
        this.ml = user.getWater().stream().filter(w -> w.getCreatedAt().toLocalDate().equals(LocalDate.now()))
                .mapToDouble(Water::getAmount).sum();
    }

}

package com.kuroko.heathyapi.feature.food;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodDto {
    private String name;
    private String imageUrl;
    private double caloriesPer100g;

}

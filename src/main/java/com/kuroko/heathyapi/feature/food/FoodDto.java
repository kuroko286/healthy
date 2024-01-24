package com.kuroko.heathyapi.feature.food;

import com.kuroko.heathyapi.components.Nutrition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {
    private String name;
    private Nutrition nutrition;
    private double calories;

    public FoodDto(Food food) {
        this.name = food.getName();
        this.nutrition = food.getNutrition();
        this.calories = food.getCalories();
    }
}

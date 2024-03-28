package com.kuroko.heathyapi.feature.food.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuroko.heathyapi.components.Nutrition;
import com.kuroko.heathyapi.feature.food.model.Food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto { // DTO common for request and responsed
    @JsonProperty("_id")
    private Long id;
    private String name;
    private Nutrition nutrition;
    private double calories;

    public FoodDto(Food food) {
        this.id = food.getId();
        this.name = food.getName();
        this.nutrition = food.getNutrition();
        this.calories = food.getCalories();
    }
}

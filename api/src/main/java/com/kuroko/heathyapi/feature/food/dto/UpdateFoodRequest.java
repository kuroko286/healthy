package com.kuroko.heathyapi.feature.food.dto;

import com.kuroko.heathyapi.feature.meal.model.MealType;

import lombok.Data;

@Data
public class UpdateFoodRequest {
    private MealType mealType;
    private FoodDto foodDetails;
}

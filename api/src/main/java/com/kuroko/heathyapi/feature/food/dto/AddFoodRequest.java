package com.kuroko.heathyapi.feature.food.dto;

import java.util.List;

import com.kuroko.heathyapi.feature.meal.model.MealType;

import lombok.Data;

@Data
public class AddFoodRequest {
    private MealType mealType;
    private List<FoodDto> foods;
}

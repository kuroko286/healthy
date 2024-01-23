package com.kuroko.heathyapi.feature.food;

import java.util.List;

import com.kuroko.heathyapi.feature.meal.MealType;

import lombok.Data;

@Data
public class AddFoodDto {
    private MealType mealType;
    private List<FoodDto> foods;
}

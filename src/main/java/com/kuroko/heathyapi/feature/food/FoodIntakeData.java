package com.kuroko.heathyapi.feature.food;

import com.kuroko.heathyapi.feature.meal.MealType;

import lombok.Data;

@Data
public class FoodIntakeData {
    private MealType mealType;
    private FoodDto foodDetails;
}

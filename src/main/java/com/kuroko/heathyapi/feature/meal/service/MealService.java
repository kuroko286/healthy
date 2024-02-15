package com.kuroko.heathyapi.feature.meal.service;

import java.time.LocalDate;
import java.util.List;

import com.kuroko.heathyapi.feature.food.dto.AddFoodDto;
import com.kuroko.heathyapi.feature.food.dto.FoodIntakeData;
import com.kuroko.heathyapi.feature.meal.dto.MealsPerDayDto;
import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.meal.model.MealType;
import com.kuroko.heathyapi.feature.user.model.User;

public interface MealService {
    List<Meal> getMealsByUserAndDate(User user, LocalDate date);

    MealsPerDayDto addFoodIntake(String email, AddFoodDto addFoodDto);

    MealsPerDayDto deleteFoodIntake(String email, MealType mealType);

    MealsPerDayDto updateFoodIntake(String email, long FoodId, FoodIntakeData mealDto);
}

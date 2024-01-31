package com.kuroko.heathyapi.feature.meal;

import java.time.LocalDate;
import java.util.List;

import com.kuroko.heathyapi.feature.food.AddFoodDto;
import com.kuroko.heathyapi.feature.food.FoodIntakeData;
import com.kuroko.heathyapi.feature.food.UpdateFoodDto;
import com.kuroko.heathyapi.feature.user.User;

public interface IMealService {
    List<Meal> getMealsByUserAndDate(User user, LocalDate date);

    MealsPerDayDto addFoodIntake(String email, AddFoodDto addFoodDto);

    MealsPerDayDto deleteFoodIntake(String email, MealType mealType);

    MealsPerDayDto updateFoodIntake(String email, long FoodId, FoodIntakeData mealDto);
}

package com.kuroko.heathyapi.feature.meal.service;

import java.time.LocalDate;
import java.util.List;

import com.kuroko.heathyapi.feature.food.dto.AddFoodRequest;
import com.kuroko.heathyapi.feature.food.dto.UpdateFoodRequest;
import com.kuroko.heathyapi.feature.meal.dto.MealsPerDayResponse;
import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.meal.model.MealType;
import com.kuroko.heathyapi.feature.user.model.User;

public interface MealService {
    List<Meal> getMealsByUserAndDate(User user, LocalDate date);

    MealsPerDayResponse addFoodIntake(Long id, AddFoodRequest foodRequest);

    MealsPerDayResponse deleteFoodIntake(Long id, MealType mealType);

}

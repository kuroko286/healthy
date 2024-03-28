package com.kuroko.heathyapi.feature.food.service;

import java.time.LocalDate;
import java.util.List;

import com.kuroko.heathyapi.feature.food.dto.UpdateFoodRequest;
import com.kuroko.heathyapi.feature.meal.dto.MealsPerDayResponse;
import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.user.model.User;

public interface FoodService {

    MealsPerDayResponse updateFoodIntake(Long id, Long foodId, UpdateFoodRequest mealDto);

    List<Meal> getMealsByUserAndDate(User user, LocalDate date);

}

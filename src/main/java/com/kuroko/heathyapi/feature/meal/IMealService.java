package com.kuroko.heathyapi.feature.meal;

import java.util.Date;
import java.util.List;

import com.kuroko.heathyapi.feature.user.User;

public interface IMealService {
    List<Meal> getMealsByUserAndDate(User user, Date date);

    void addFoodIntake(String token, MealDto mealDto);

    void deleteFoodIntake(String token, MealDto mealDto);

    void updateFoodIntake(String token, MealDto mealDto, Long id);
}

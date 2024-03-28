package com.kuroko.heathyapi.feature.meal.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.food.FoodRepository;
import com.kuroko.heathyapi.feature.food.dto.AddFoodRequest;
import com.kuroko.heathyapi.feature.food.dto.FoodDto;
import com.kuroko.heathyapi.feature.food.dto.UpdateFoodRequest;
import com.kuroko.heathyapi.feature.food.model.Food;
import com.kuroko.heathyapi.feature.meal.MealRepository;
import com.kuroko.heathyapi.feature.meal.dto.MealsPerDayResponse;
import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.meal.model.MealType;
import com.kuroko.heathyapi.feature.user.UserRepository;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.exception.business.*;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Meal> getMealsByUserAndDate(User user, LocalDate date) {
        return mealRepository.findByUserAndTimeRange(user, date.atStartOfDay(), date.atStartOfDay().plusDays(1));
    }

    @Override
    public MealsPerDayResponse addFoodIntake(Long id, AddFoodRequest foodRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User with id " + id + " not found."));
        Meal meal = mealRepository
                .findByUserAndDateRangeAndType(user, LocalDate.now().atStartOfDay(),
                        LocalDate.now().atStartOfDay().plusDays(1), foodRequest.getMealType())
                .orElse(new Meal()); // may not created yet
        meal.setType(foodRequest.getMealType());
        meal.setUser(user);
        List<Food> foods = foodRequest.getFoods().stream().map((f) -> mapToFood(f)).toList();
        if (meal.getFoods() != null) {
            meal.getFoods().addAll(foods);
        } else {
            meal.setFoods(foods);
        }
        for (int i = 0; i < foods.size(); i++) {
            foods.get(i).setMeal(meal);
        }
        mealRepository.save(meal);

        return new MealsPerDayResponse(getMealsByUserAndDate(user, LocalDate.now()));
    }

    @Override
    public MealsPerDayResponse deleteFoodIntake(Long id, MealType mealType) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User with id " + id + " not found."));
        Meal meal = mealRepository.findByUserAndDateRangeAndType(user, LocalDate.now().atStartOfDay(),
                LocalDate.now().atStartOfDay().plusDays(1), mealType).orElseThrow(
                        () -> new ResourceNotFoundException("Meal with mealType " + mealType + " not found."));
        if (meal != null) {
            mealRepository.delete(meal);
        }

        return new MealsPerDayResponse(getMealsByUserAndDate(user, LocalDate.now()));
    }

    public Food mapToFood(FoodDto foodDto) {
        Food food = new Food();
        food.setName(foodDto.getName());
        food.setCalories(foodDto.getCalories());
        food.setNutrition(foodDto.getNutrition());
        return food;
    }
}

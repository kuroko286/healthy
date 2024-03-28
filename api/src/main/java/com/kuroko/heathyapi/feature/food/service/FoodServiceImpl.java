package com.kuroko.heathyapi.feature.food.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.food.FoodRepository;
import com.kuroko.heathyapi.feature.food.dto.UpdateFoodRequest;
import com.kuroko.heathyapi.feature.food.model.Food;
import com.kuroko.heathyapi.feature.meal.MealRepository;
import com.kuroko.heathyapi.feature.meal.dto.MealsPerDayResponse;
import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.user.UserRepository;
import com.kuroko.heathyapi.feature.user.model.User;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MealRepository mealRepository;

    @Override
    public MealsPerDayResponse updateFoodIntake(Long id, Long foodId, UpdateFoodRequest updateFoodDto) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User with id " + id + " not found."));

        Food food = foodRepository.findById(foodId).orElseThrow(
                () -> new ResourceNotFoundException("Food with id " + foodId + " not found."));
        food.setName(updateFoodDto.getFoodDetails().getName());
        food.setCalories(updateFoodDto.getFoodDetails().getCalories());
        food.setNutrition(updateFoodDto.getFoodDetails().getNutrition());
        foodRepository.save(food);
        return new MealsPerDayResponse(getMealsByUserAndDate(user, LocalDate.now()));
    }

    @Override
    public List<Meal> getMealsByUserAndDate(User user, LocalDate date) {
        return mealRepository.findByUserAndTimeRange(user, date.atStartOfDay(), date.atStartOfDay().plusDays(1));
    }

}

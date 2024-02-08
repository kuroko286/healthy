package com.kuroko.heathyapi.feature.meal.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.food.FoodRepository;
import com.kuroko.heathyapi.feature.food.dto.AddFoodDto;
import com.kuroko.heathyapi.feature.food.dto.FoodDto;
import com.kuroko.heathyapi.feature.food.dto.FoodIntakeData;
import com.kuroko.heathyapi.feature.food.model.Food;
import com.kuroko.heathyapi.feature.meal.MealRepository;
import com.kuroko.heathyapi.feature.meal.dto.MealsPerDayDto;
import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.meal.model.MealType;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.exception.business.*;

@Service
public class MealService implements IMealService {
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<Meal> getMealsByUserAndDate(User user, LocalDate date) {
        return mealRepository.findByUserAndDateRange(user, date.atStartOfDay(), date.atStartOfDay().plusDays(1));
    }

    @Override
    public MealsPerDayDto addFoodIntake(String email, AddFoodDto addFoodDto) {
        Account account = accountRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();
        Meal meal = mealRepository
                .findByUserAndDateRangeAndType(user, LocalDate.now().atStartOfDay(),
                        LocalDate.now().atStartOfDay().plusDays(1), addFoodDto.getMealType())
                .orElse(new Meal());
        meal.setType(addFoodDto.getMealType());
        meal.setUser(user);
        mealRepository.save(meal);
        List<Food> foods = addFoodDto.getFoods().stream().map((f) -> mapToFood2(f, meal)).toList();
        if (foods.size() > 0) {
            foodRepository.saveAll(foods);
        }
        return new MealsPerDayDto(getMealsByUserAndDate(user, LocalDate.now()));
    }

    @Override
    public MealsPerDayDto deleteFoodIntake(String email, MealType mealType) {
        Account account = accountRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();
        Meal meal = mealRepository.findByUserAndDateRangeAndType(user, LocalDate.now().atStartOfDay(),
                LocalDate.now().atStartOfDay().plusDays(1), mealType).orElseThrow(
                        () -> new ResourceNotFoundException("Meal with mealType " + mealType + " not found."));
        foodRepository.deleteAll(meal.getFoods());

        return new MealsPerDayDto(getMealsByUserAndDate(user, LocalDate.now()));
    }

    @Override
    public MealsPerDayDto updateFoodIntake(String email, long foodId, FoodIntakeData updateFoodDto) {

        Account account = accountRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();

        Food food = foodRepository.findById(foodId).orElseThrow(
                () -> new ResourceNotFoundException("Food with id " + foodId + " not found."));
        food.setName(updateFoodDto.getFoodDetails().getName());
        food.setCalories(updateFoodDto.getFoodDetails().getCalories());
        food.setNutrition(updateFoodDto.getFoodDetails().getNutrition());
        foodRepository.save(food);
        return new MealsPerDayDto(getMealsByUserAndDate(user, LocalDate.now()));
    }

    public Food mapToFood(FoodDto foodDto) {
        Food food = new Food();
        food.setName(foodDto.getName());
        food.setCalories(foodDto.getCalories());
        food.setNutrition(foodDto.getNutrition());
        return food;
    }

    public Food mapToFood2(FoodDto foodDto, Meal meal) {
        Food food = new Food();
        food.setName(foodDto.getName());
        food.setCalories(foodDto.getCalories());
        food.setNutrition(foodDto.getNutrition());
        food.setMeal(meal);
        return food;
    }
}

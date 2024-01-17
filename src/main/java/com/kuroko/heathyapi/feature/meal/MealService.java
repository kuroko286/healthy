package com.kuroko.heathyapi.feature.meal;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.feature.user.User;

@Service
public class MealService implements IMealService {
    @Autowired
    private MealRepository mealRepository;

    @Override
    public List<Meal> getMealsByUserAndDate(User user, Date date) {

        return mealRepository.findByUserAndCreatedAt(user, date);
    }

    @Override
    public void addFoodIntake(String token, MealDto mealDto) {
        // TODO:
    }

    @Override
    public void deleteFoodIntake(String token, MealDto mealDto) {
        // TODO

    }

    @Override
    public void updateFoodIntake(String token, MealDto mealDto, Long id) {
        // TODO
    }

}

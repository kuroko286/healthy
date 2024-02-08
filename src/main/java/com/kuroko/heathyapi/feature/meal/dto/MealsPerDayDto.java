package com.kuroko.heathyapi.feature.meal.dto;

import java.util.List;

import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.meal.model.MealType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MealsPerDayDto {

        private double totalConsumedCaloriesPerDay;
        private double totalConsumedCarbohydratesPerDay;
        private double totalConsumedFatPerDay;
        private double totalConsumedProteinPerDay;

        private MealDto breakfast;
        private MealDto lunch;
        private MealDto dinner;
        private MealDto snack;

        public MealsPerDayDto(List<Meal> meals) {
                this.breakfast = new MealDto(
                                meals.stream().filter(m -> m.getType().equals(MealType.BREAKFAST)).findFirst()
                                                .orElse(null));
                this.lunch = new MealDto(
                                meals.stream().filter(m -> m.getType().equals(MealType.LUNCH)).findFirst()
                                                .orElse(null));
                this.dinner = new MealDto(
                                meals.stream().filter(m -> m.getType().equals(MealType.DINNER)).findFirst()
                                                .orElse(null));
                this.snack = new MealDto(
                                meals.stream().filter(m -> m.getType().equals(MealType.SNACK)).findFirst()
                                                .orElse(null));

                this.totalConsumedCarbohydratesPerDay = this.breakfast.getTotalCarbohydrates()
                                + this.lunch.getTotalCarbohydrates() + this.dinner.getTotalCarbohydrates()
                                + this.snack.getTotalCarbohydrates();
                this.totalConsumedFatPerDay = this.breakfast.getTotalFat() +
                                this.lunch.getTotalFat()
                                + this.dinner.getTotalFat() + this.snack.getTotalFat();
                this.totalConsumedProteinPerDay = this.breakfast.getTotalProtein() +
                                this.lunch.getTotalProtein()
                                + this.dinner.getTotalProtein() + this.snack.getTotalProtein();
                this.totalConsumedCaloriesPerDay = this.breakfast.getTotalCalories() +
                                this.lunch.getTotalCalories()
                                + this.dinner.getTotalCalories() + this.snack.getTotalCalories();
        }

}

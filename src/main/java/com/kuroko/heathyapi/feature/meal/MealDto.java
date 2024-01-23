package com.kuroko.heathyapi.feature.meal;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.kuroko.heathyapi.feature.food.Food;
import com.kuroko.heathyapi.feature.food.FoodDto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class MealDto {
    private long id;
    private double totalCarbohydrates;
    private double totalProtein;
    private double totalFat;
    private double totalCalories;

    // @Enumerated(EnumType.STRING)
    // private MealType type;
    // private Date createdAt;
    // private Date updatedAt;
    private List<FoodDto> foods;

    public MealDto(Meal meal) {
        if (meal == null) {
            return;
        }
        this.id = meal.getId();
        this.foods = meal.getFoods().stream().map((f) -> new FoodDto(f)).toList();
        this.totalCarbohydrates = meal.getFoods().stream().mapToDouble(f -> f.getNutrition().getCarbohydrates()).sum();
        this.totalProtein = meal.getFoods().stream().mapToDouble(f -> f.getNutrition().getProtein()).sum();
        this.totalFat = meal.getFoods().stream().mapToDouble(f -> f.getNutrition().getFat()).sum();
        this.totalCalories = meal.getFoods().stream().mapToDouble(f -> f.getCalories()).sum();
    }

    public MealDto() {
        this.foods = List.of();
    }

}

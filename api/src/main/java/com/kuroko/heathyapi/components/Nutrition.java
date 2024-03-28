package com.kuroko.heathyapi.components;

import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.util.NutritionCaculator;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Nutrition { // in grams
    private int carbohydrates;
    private int protein;
    private int fat;

    public Nutrition(User user) {
        this.carbohydrates = NutritionCaculator.caculateCarbonhydrates(user);
        this.protein = NutritionCaculator.caculateProtein(user);
        this.fat = NutritionCaculator.caculateFat(user);
    }
}

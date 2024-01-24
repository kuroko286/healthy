package com.kuroko.heathyapi.components;

import com.kuroko.heathyapi.feature.user.User;
import com.kuroko.heathyapi.util.UserUtil;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Nutrition {
    private double carbohydrates;
    private double protein;
    private double fat;

    public Nutrition(User user) {
        this.carbohydrates = UserUtil.caculateCarbonhydrates(user);
        this.protein = UserUtil.caculateProtein(user);
        this.fat = UserUtil.caculateFat(user);
    }
}

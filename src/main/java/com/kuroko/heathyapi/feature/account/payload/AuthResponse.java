package com.kuroko.heathyapi.feature.account.payload;

import java.util.List;

import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.meal.Meal;
import com.kuroko.heathyapi.feature.user.User;
import com.kuroko.heathyapi.feature.water.Water;
import com.kuroko.heathyapi.util.UserUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String id;
    private String name;
    private String email;
    private String avatarURL;
    private String goal;
    private String gender;
    private int age;
    private double height;
    private double weight;
    private double coefficientOfActivity;

    private double carbohydrates;
    private double protein;
    private double fat;
    private double calories;
    private double water;
    private List<Meal> consumedMealsByDay;
    private List<Water> consumedWaterByDay;

    public AuthResponse(String token, User user) {
        Account account = user.getAccount();
        this.token = token;
        this.id = user.getId().toString();
        this.name = user.getName();
        this.email = account.getEmail();
        this.avatarURL = user.getAvatarURL();
        this.goal = user.getGoal();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.coefficientOfActivity = user.getCoefficientOfActivity();
        this.carbohydrates = UserUtil.caculateCarbonhydrates(user);
        this.protein = UserUtil.caculateProtein(user);
        this.fat = UserUtil.caculateFat(user);
        this.calories = UserUtil.caculateDailyCalories(user);
        this.water = UserUtil.caculateDailyWater(user);
    }
}

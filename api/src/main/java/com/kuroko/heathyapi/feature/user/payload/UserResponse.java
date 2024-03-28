package com.kuroko.heathyapi.feature.user.payload;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuroko.heathyapi.components.Nutrition;
import com.kuroko.heathyapi.feature.meal.dto.MealsPerDayResponse;
import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.feature.water.Water;
import com.kuroko.heathyapi.feature.water.dto.WaterDto;
import com.kuroko.heathyapi.util.NutritionCaculator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    @JsonProperty("info")
    private UserWrapper userWrapper;
    private MealsPerDayResponse consumedMealsByDay;
    private WaterDto consumedWaterByDay;

    public UserResponse(User user, List<Meal> meals, List<Water> water) {
        this.userWrapper = new UserWrapper(user);
        this.consumedMealsByDay = new MealsPerDayResponse(meals);
        this.consumedWaterByDay = new WaterDto(water);
    }

    public Optional<MealsPerDayResponse> getMealPerDay(User user) {
        if (user.getMeals() == null) {
            return Optional.empty();
        }
        return Optional.of(new MealsPerDayResponse(
                user.getMeals().stream().filter(m -> m.getCreatedAt().toLocalDate().equals(LocalDate.now())).toList()));
    }

    public long getUserId() {
        return userWrapper.getId();
    }

}

@Data
class UserWrapper {
    private long id;
    private String name;
    private String email;
    private String goal;
    private String gender;
    private String avatarURL;
    private int age;
    private double height;
    private double coefficientOfActivity;
    private double weight; // kg
    private Nutrition dailyNutrition;
    private int dailyCalories;
    private int dailyWater;

    public UserWrapper(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getAccount().getEmail();
        this.goal = user.getGoal().toString();
        this.gender = user.getGender().toString();
        this.avatarURL = user.getAvatarURL() == null ? "" : user.getAvatarURL();
        this.age = user.getAge();
        this.height = user.getHeight();
        this.coefficientOfActivity = user.getCoefficientOfActivity();
        this.weight = user.getWeight();
        this.dailyNutrition = new Nutrition(user);
        this.dailyCalories = NutritionCaculator.caculateDailyCalories(user);
        this.dailyWater = NutritionCaculator.caculateDailyWater(user);
    }
}

package com.kuroko.heathyapi.feature.user.payload;

import java.time.LocalDate;
import java.util.Optional;

import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.components.Nutrition;
import com.kuroko.heathyapi.feature.meal.MealsPerDayDto;
import com.kuroko.heathyapi.feature.user.User;
import com.kuroko.heathyapi.feature.water.WaterDto;
import com.kuroko.heathyapi.util.UserUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
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
    private double dailyCalories;
    private double dailyWater;
    private MealsPerDayDto consumedMealsByDay;
    private WaterDto consumedWaterByDay;

    public UserDto(User user, Account account) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = account.getEmail();
        this.goal = user.getGoal();
        this.gender = user.getGender();
        this.avatarURL = user.getAvatarURL();
        this.age = user.getAge();
        this.height = user.getHeight();
        this.coefficientOfActivity = user.getCoefficientOfActivity();
        this.weight = user.getWeight();
        this.dailyNutrition = new Nutrition(user);
        this.dailyCalories = UserUtil.caculateDailyCalories(user);
        this.dailyWater = UserUtil.caculateDailyWater(user);
        if (user.getMeals() == null) {
            this.consumedMealsByDay = new MealsPerDayDto();
        } else {
            this.consumedMealsByDay = new MealsPerDayDto(
                    user.getMeals().stream().filter(m -> m.getCreatedAt().toLocalDate().equals(LocalDate.now()))
                            .toList());
        }
        if (user.getWater() == null) {
            this.consumedWaterByDay = new WaterDto();
        } else {
            this.consumedWaterByDay = new WaterDto(user);
        }
    }

    public Optional<MealsPerDayDto> getMealPerDay(User user) {
        if (user.getMeals() == null) {
            return Optional.empty();
        }
        return Optional.of(new MealsPerDayDto(
                user.getMeals().stream().filter(m -> m.getCreatedAt().toLocalDate().equals(LocalDate.now())).toList()));
    }

}

package com.kuroko.heathyapi.util;

import org.springframework.stereotype.Component;

import com.kuroko.heathyapi.feature.user.model.Gender;
import com.kuroko.heathyapi.feature.user.model.Goal;
import com.kuroko.heathyapi.feature.user.model.User;

@Component
public class NutritionCaculator {
    public static int caculateCarbonhydrates(User user) {
        final double recommendedPercent = 0.55;
        return (int) (caculateDailyCalories(user) * recommendedPercent / 4);
    }

    public static int caculateProtein(User user) {
        double recommendedPercent;
        double calories = caculateDailyCalories(user);
        if (user.getAge() <= 3) {
            recommendedPercent = 0.15;
        } else {
            recommendedPercent = 0.25;
        }
        return (int) (recommendedPercent * calories / 4);
    }

    public static int caculateFat(User user) {
        double recommendedPercent;
        double calories = caculateDailyCalories(user);
        if (user.getAge() <= 3) {
            recommendedPercent = 0.35;
        } else if (user.getAge() <= 18) {
            recommendedPercent = 0.3;
        } else {
            recommendedPercent = 0.25;
        }
        return (int) (recommendedPercent * calories / 9);
    }

    public static int caculateDailyCalories(User user) {
        Goal goal = user.getGoal();
        double res;
        switch (goal) {
            case LOSE_WEIGHT:
                res = caculateTDEE(user) - 500;
                break;
            case MAINTAIN:
                res = caculateTDEE(user);
                break;
            case GAIN_MUSCLE:
                res = caculateTDEE(user) + 500;
                break;
            default:
                res = caculateTDEE(user);
                break;
        }
        return (int) res;
    }

    public static int caculateDailyWater(User user) {
        return caculateDailyCalories(user); // 1 kcal = 1 mL water intake
    }

    public static double caculateBMR(User user) {
        double baseBmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge();
        if (user.getGender().toString().equals(Gender.MALE.toString())) {
            return baseBmr + 5;
        } else if (user.getGender().toString().equals(Gender.FEMALE.toString())) {
            return baseBmr - 161;
        }
        return baseBmr;
    }

    public static double caculateTDEE(User user) {
        double tdee = caculateBMR(user) * user.getCoefficientOfActivity();
        return tdee;
    }

}

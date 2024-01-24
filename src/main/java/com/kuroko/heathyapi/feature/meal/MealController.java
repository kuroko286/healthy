package com.kuroko.heathyapi.feature.meal;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuroko.heathyapi.feature.food.AddFoodDto;
import com.kuroko.heathyapi.feature.food.UpdateFoodDto;

@RestController
@RequestMapping("/v1/meals")
public class MealController {
    @Autowired
    private IMealService mealServicer;

    @PostMapping("/food-intake")
    public ResponseEntity<MealsPerDayDto> addFoodIntake(@RequestAttribute("email") String email,
            @RequestBody AddFoodDto addFoodDto) {
        MealsPerDayDto response = mealServicer.addFoodIntake(email, addFoodDto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/food-intake")
    public ResponseEntity<MealsPerDayDto> updateFoodIntake(@RequestAttribute("email") String email,
            @RequestBody UpdateFoodDto mealDto) {
        MealsPerDayDto response = mealServicer.updateFoodIntake(email, mealDto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/food-intake")
    public ResponseEntity<MealsPerDayDto> deleteFoodIntake(@RequestAttribute("email") String email,
            @RequestBody String mealType) {
        System.out.println(mealType);
        Map<String, MealType> mealTypeMap = Map.of("breakfast", MealType.BREAKFAST, "lunch", MealType.LUNCH, "dinner",
                MealType.DINNER, "snack", MealType.SNACK);
        MealsPerDayDto response = mealServicer.deleteFoodIntake(email, mealTypeMap.get(mealType));
        return ResponseEntity.ok().body(response);
    }

}

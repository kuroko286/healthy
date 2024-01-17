package com.kuroko.heathyapi.feature.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/meals")
public class MealController {
    @Autowired
    private IMealService mealServicer;

    @PostMapping("/food-intake")
    public ResponseEntity<?> addFoodIntake(@RequestHeader("Authorization") String token, @RequestBody MealDto mealDto) {
        mealServicer.addFoodIntake(token, mealDto);
        return ResponseEntity.ok().body("Food intake added successfully");
    }

    @PutMapping("/food-intake/:id")
    public ResponseEntity<?> updateFoodIntake(@RequestHeader("Authorization") String token,
            @RequestBody MealDto mealDto, @RequestParam Long id) {
        mealServicer.updateFoodIntake(token, mealDto, id);
        return ResponseEntity.ok().body("Food intake updated successfully");
    }

    @DeleteMapping("/food-intake")
    public ResponseEntity<?> deleteFoodIntake(@RequestHeader("Authorization") String token,
            @RequestBody MealDto mealDto) {
        mealServicer.deleteFoodIntake(token, mealDto);
        return ResponseEntity.ok().body("Food intake deleted successfully");
    }

}

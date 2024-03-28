package com.kuroko.heathyapi.feature.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kuroko.heathyapi.components.Nutrition;
import com.kuroko.heathyapi.feature.food.dto.AddFoodRequest;
import com.kuroko.heathyapi.feature.food.dto.UpdateFoodRequest;
import com.kuroko.heathyapi.feature.food.service.FoodService;
import com.kuroko.heathyapi.feature.meal.dto.MealsPerDayResponse;
import com.kuroko.heathyapi.feature.meal.model.MealType;
import com.kuroko.heathyapi.feature.meal.service.MealService;
import com.kuroko.heathyapi.feature.user.payload.GoalRequest;
import com.kuroko.heathyapi.feature.user.payload.GoalResponse;
import com.kuroko.heathyapi.feature.user.payload.StatisticsResponse;
import com.kuroko.heathyapi.feature.user.payload.UserResponse;
import com.kuroko.heathyapi.feature.user.payload.UserRequest;
import com.kuroko.heathyapi.feature.user.service.UserService;
import com.kuroko.heathyapi.feature.water.dto.WaterDto;
import com.kuroko.heathyapi.feature.water.service.WaterService;
import com.kuroko.heathyapi.feature.weight.dto.WeightDto;
import com.kuroko.heathyapi.feature.weight.dto.WeightUpdatedDto;
import com.kuroko.heathyapi.feature.weight.service.WeightService;

import lombok.Data;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private WaterService waterService;
    @Autowired
    private WeightService weightService;
    @Autowired
    private MealService mealService;

    // User info
    @GetMapping("/{id}/statistics")
    public ResponseEntity<StatisticsResponse> getStatistics(@RequestParam int month, @RequestParam int year,
            @PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getStatistics(month, year, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getCurrentUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getCurrentUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
            @RequestBody UserRequest userData) {
        UserResponse user = userService.updateUserInfo(id, userData);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}/goal")
    public ResponseEntity<GoalResponse> updateUserGoal(@PathVariable Long id,
            @RequestBody GoalRequest goal) {
        GoalResponse goalUpdated = userService.updateUserGoal(id, goal);
        return ResponseEntity.ok().body(goalUpdated);
    }

    @PostMapping("/{id}/avatar")
    public ResponseEntity<String> updateUserAvatar(@PathVariable Long id,
            @RequestPart("avatar") MultipartFile avatar) {
        userService.updateUserAvatar(id, avatar);
        return ResponseEntity.ok().body("Avatar updated successfully");
    }

    // User water and meals
    @PutMapping("/{id}/foods/{foodId}")
    public ResponseEntity<MealsPerDayResponse> updateFoodIntake(@PathVariable Long id,
            @PathVariable Long foodId,
            @RequestBody UpdateFoodRequest mealDto) {
        MealsPerDayResponse response = foodService.updateFoodIntake(id, foodId, mealDto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}/foods/recommended")
    public ResponseEntity<List<?>> getRecommendedFood() {
        @Data
        class RecommendedFood {
            private String name;
            private double calories;
            private Nutrition nutrition;
            private String amount; // in gams
            private String img;

            public RecommendedFood() {
                this.name = "apple";
                this.calories = 52;
                this.nutrition = new Nutrition(100, 100, 100);
                this.amount = "100 g";
                this.img = "https://i.postimg.cc/MGt1HKQm/86998b106cdc2cf1608266f52e43d596.jpg";
            }
        }

        return ResponseEntity.ok()
                .body(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream().map((i) -> new RecommendedFood()).toList());
    }

    @PostMapping("/{id}/foods")
    public ResponseEntity<MealsPerDayResponse> addFoodIntake(@PathVariable Long id,
            @RequestBody AddFoodRequest foodRequest) {
        MealsPerDayResponse response = mealService.addFoodIntake(id, foodRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}/meals")
    public ResponseEntity<MealsPerDayResponse> deleteFoodIntake(@PathVariable Long id,
            @RequestParam String type) {
        MealsPerDayResponse response = mealService.deleteFoodIntake(id, MealType.getType(type));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{id}/waters")
    public ResponseEntity<WaterDto> addWaterIntake(@PathVariable Long id,
            @RequestBody WaterDto waterDto) {
        WaterDto water = waterService.addWaterIntake(id, waterDto);
        return ResponseEntity.ok().body(water);
    }

    @DeleteMapping("/{id}/waters")
    public ResponseEntity<WaterDto> deleteWaterIntake(@PathVariable Long id) {
        WaterDto water = waterService.deleteWaterIntake(id);
        return ResponseEntity.ok().body(water);
    }

    @PostMapping("/{id}/weights")
    public ResponseEntity<WeightUpdatedDto> create(@PathVariable Long id,
            @RequestBody WeightDto weightDto) {
        WeightUpdatedDto weight = weightService.createWeight(id, weightDto);
        return ResponseEntity.ok().body(weight);
    }
}

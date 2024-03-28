package com.kuroko.heathyapi.feature.meal;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuroko.heathyapi.feature.food.dto.AddFoodRequest;
import com.kuroko.heathyapi.feature.food.dto.UpdateFoodRequest;
import com.kuroko.heathyapi.feature.meal.dto.MealsPerDayResponse;
import com.kuroko.heathyapi.feature.meal.model.MealType;
import com.kuroko.heathyapi.feature.meal.service.MealService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/meals")
public class MealController {
    @Autowired
    private MealService mealService;

}

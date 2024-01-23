package com.kuroko.heathyapi.feature.food;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/v1/foods")
public class FoodController {
    @GetMapping("/recommended-food")
    public ResponseEntity<List<FoodDto>> getRecommendedFood() {
        // TODO: Implement recommended food
        return ResponseEntity.ok()
                .body(List.of(1, 2, 3).stream().map((i) -> new FoodDto()).toList());
    }

}

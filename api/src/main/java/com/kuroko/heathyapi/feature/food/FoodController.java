package com.kuroko.heathyapi.feature.food;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuroko.heathyapi.components.Nutrition;

import lombok.Data;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/v1/foods")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FoodController {

    @GetMapping("/recommended-food")
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
        // TODO: Implement recommended food
        return ResponseEntity.ok()
                .body(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream().map((i) -> new RecommendedFood()).toList());
    }

}

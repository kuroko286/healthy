package com.kuroko.heathyapi.feature.food;

import lombok.Data;

@Data
public class UpdateFoodDto {
    private Long foodId;
    private FoodIntakeData foodIntakeData;
}

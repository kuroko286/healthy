package com.kuroko.heathyapi.feature.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.feature.food.FoodRepository;

@Service
public class FoodService implements IFoodService {
    @Autowired
    private FoodRepository foodRepository;

}

package com.kuroko.heathyapi.feature.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService implements IFoodService {
    @Autowired
    private FoodRepository foodRepository;

}

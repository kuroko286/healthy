package com.kuroko.heathyapi.feature.food;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuroko.heathyapi.feature.food.model.Food;
import com.kuroko.heathyapi.feature.user.model.User;

public interface FoodRepository extends JpaRepository<Food, Long> {
    @Query("SELECT day(f.createdAt) as day , SUM(f.calories) from Food f where f.meal.user = :user and year(f.createdAt) = :year and month(f.createdAt) = :month group by day(f.createdAt)")
    List<Object[]> findByYearAndMonthAndUser(@Param("year") int year, @Param("month") int month,
            @Param("user") User user);

}

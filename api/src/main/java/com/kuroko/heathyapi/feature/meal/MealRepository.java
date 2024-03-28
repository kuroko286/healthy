package com.kuroko.heathyapi.feature.meal;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.meal.model.MealType;
import com.kuroko.heathyapi.feature.user.model.User;

public interface MealRepository extends JpaRepository<Meal, Long> {
        @Query("select m from Meal m where m.user = :user and m.createdAt between :startDate and :endDate")
        List<Meal> findByUserAndTimeRange(@Param("user") User user, @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        @Query("select m from Meal m where m.user = :user and m.createdAt between :startDate and :endDate and m.type = :type")
        Optional<Meal> findByUserAndDateRangeAndType(@Param("user") User user,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate, @Param("type") MealType type);
}

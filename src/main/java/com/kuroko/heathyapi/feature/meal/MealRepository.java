package com.kuroko.heathyapi.feature.meal;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kuroko.heathyapi.feature.user.User;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserAndCreatedAt(User user, Date createdAt);
}

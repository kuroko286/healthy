package com.kuroko.heathyapi.feature.meal.model;

import java.time.LocalDateTime;
import java.util.*;

import org.hibernate.annotations.CreationTimestamp;

import com.kuroko.heathyapi.feature.food.model.Food;
import com.kuroko.heathyapi.feature.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private MealType type;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany(orphanRemoval = true, mappedBy = "meal", cascade = CascadeType.ALL)
    private List<Food> foods = new ArrayList<>();

}

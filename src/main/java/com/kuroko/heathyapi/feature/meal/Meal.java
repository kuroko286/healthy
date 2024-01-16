package com.kuroko.heathyapi.feature.meal;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.kuroko.heathyapi.feature.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Meal {
    private long id;
    private String name;
    private double carbonohidrates;
    private double protein;
    private double fat;
    private double calories;
    private MealType type;
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}

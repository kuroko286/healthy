package com.kuroko.heathyapi.feature.user;

import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.meal.Meal;
import com.kuroko.heathyapi.feature.water.Water;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String goal;
    private String gender;
    private int age;
    private double height;
    private double weight;
    private double coefficientOfActivity;
    @OneToOne
    private Account account;
    @OneToMany(orphanRemoval = true, mappedBy = "user") // when remote water from this list, it also delele from db
    private List<Water> water;
    @OneToMany(orphanRemoval = true, mappedBy = "user")
    private List<Meal> meal;

}

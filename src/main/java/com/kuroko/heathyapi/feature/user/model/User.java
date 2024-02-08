package com.kuroko.heathyapi.feature.user.model;

import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.chatgpt.model.ChatMessage;
import com.kuroko.heathyapi.feature.meal.model.Meal;
import com.kuroko.heathyapi.feature.water.Water;
import com.kuroko.heathyapi.feature.weight.Weight;

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
    private String avatarURL;
    private int age;
    private double height;
    private double weight;
    private double coefficientOfActivity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
    @OneToMany(orphanRemoval = true, mappedBy = "user", cascade = CascadeType.ALL) // when remote water from this list,
                                                                                   // it also delele from db
    private List<Water> water;
    @OneToMany(orphanRemoval = true, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Weight> weights;
    @OneToMany(orphanRemoval = true, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Meal> meals;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatMessage> messages;

}

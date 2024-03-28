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
@RequiredArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Goal goal;
    @Enumerated(EnumType.STRING)
    private Gender gender;
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
    private List<Water> water = new ArrayList<>();
    @OneToMany(orphanRemoval = true, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Weight> weights = new ArrayList<>();
    @OneToMany(orphanRemoval = true, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Meal> meals = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatMessage> messages = new ArrayList<>();

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", goal=" + goal + ", gender=" + gender + ", avatarURL="
                + avatarURL + ", age=" + age + ", height=" + height + ", weight=" + weight + ", coefficientOfActivity="
                + coefficientOfActivity + "]";
    }

}

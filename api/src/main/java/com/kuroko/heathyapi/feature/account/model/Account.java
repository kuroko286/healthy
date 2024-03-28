package com.kuroko.heathyapi.feature.account.model;

import com.kuroko.heathyapi.feature.oauth2.AuthProvider;
import com.kuroko.heathyapi.feature.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private User user;

}

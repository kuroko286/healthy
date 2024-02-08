package com.kuroko.heathyapi.components;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.kuroko.heathyapi.feature.account.model.Account;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {
    private Account account;
    private Map<String, Object> attributes;

    public CustomUserDetails(Account account) {
        this.account = account;
    }

    public CustomUserDetails(Account account, Map<String, Object> attributes) {
        this.account = account;
        this.attributes = attributes;
    }

    public long getId() {
        return account.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(account.getRole().name()));
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(account.getId());
    }

}

package com.kuroko.heathyapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.CustomUserDetails;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            Account account = accountRepository.findByEmail(email)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Account with email " + email + " not found"));

            return new CustomUserDetails(account);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
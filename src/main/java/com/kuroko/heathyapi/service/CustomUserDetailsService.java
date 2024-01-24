package com.kuroko.heathyapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
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
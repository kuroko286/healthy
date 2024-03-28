package com.kuroko.heathyapi.feature.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kuroko.heathyapi.feature.account.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findById(long id);

    boolean existsByEmail(String email);
}

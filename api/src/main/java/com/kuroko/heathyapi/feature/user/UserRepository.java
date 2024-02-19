package com.kuroko.heathyapi.feature.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kuroko.heathyapi.feature.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

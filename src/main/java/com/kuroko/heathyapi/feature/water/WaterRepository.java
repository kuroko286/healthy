package com.kuroko.heathyapi.feature.water;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kuroko.heathyapi.feature.user.User;

public interface WaterRepository extends JpaRepository<Water, Long> {
    List<Water> findByUserAndCreatedAt(User user, Date date);
}

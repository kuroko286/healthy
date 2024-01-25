package com.kuroko.heathyapi.feature.water;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuroko.heathyapi.feature.user.User;

import jakarta.transaction.Transactional;

public interface WaterRepository extends JpaRepository<Water, Long> {
        List<Water> findByUserAndCreatedAt(User user, Date date);

        @Transactional
        @Modifying
        @Query("DELETE FROM Water w WHERE w.user = :user AND w.createdAt between :startDate AND :endDate")
        void deleteByUserAndCreatedAt(@Param("user") User user, @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        @Query("SELECT day(w.createdAt) as day, SUM(w.amount) as ml from Water w where w.user = :user and year(w.createdAt) = :year and month(w.createdAt) = :month group by day(w.createdAt)")
        List<Object[]> findByYearAndMonthAndUser(@Param("year") int year, @Param("month") int month,
                        @Param("user") User user);
}

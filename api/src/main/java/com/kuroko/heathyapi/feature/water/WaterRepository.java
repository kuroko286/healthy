package com.kuroko.heathyapi.feature.water;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuroko.heathyapi.feature.user.model.User;

import jakarta.transaction.Transactional;

public interface WaterRepository extends JpaRepository<Water, Long> {
        @Query("select w from Water w where w.user = :user and w.createdAt between :start and :end")
        List<Water> findByUserAndTimeRange(User user, LocalDateTime start, LocalDateTime end);

        @Transactional
        @Modifying
        @Query("DELETE FROM Water w WHERE w.user = :user AND w.createdAt between :startDate AND :endDate")
        void deleteByUserAndTimeRange(@Param("user") User user, @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        @Query("SELECT day(w.createdAt) as day, SUM(w.amount) as ml from Water w where w.user = :user and year(w.createdAt) = :year and month(w.createdAt) = :month group by day(w.createdAt)")
        List<Object[]> findByYearAndMonthAndUser(@Param("year") int year, @Param("month") int month,
                        @Param("user") User user);
}

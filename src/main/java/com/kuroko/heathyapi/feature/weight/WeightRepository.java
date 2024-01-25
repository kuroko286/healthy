package com.kuroko.heathyapi.feature.weight;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuroko.heathyapi.feature.user.User;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    @Query("SELECT day(w.createdAt) as day, SUM(w.weight) as weight from Weight w where w.user = :user and year(w.createdAt) = :year and month(w.createdAt) = :month group by day(w.createdAt)")
    List<WeightPD> findByYearAndMonthAndUser(@Param("year") int year, @Param("month") int month,
            @Param("user") User user);
}

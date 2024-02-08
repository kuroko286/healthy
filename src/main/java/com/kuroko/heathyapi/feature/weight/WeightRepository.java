package com.kuroko.heathyapi.feature.weight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuroko.heathyapi.feature.user.model.User;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    @Query("SELECT day(w.createdAt) as day, SUM(w.weight) as weight from Weight w where w.user = :user and year(w.createdAt) = :year and month(w.createdAt) = :month group by day(w.createdAt)")
    List<Object[]> findByYearAndMonthAndUser(@Param("year") int year, @Param("month") int month,
            @Param("user") User user);

    @Query("Select w from Weight w where w.user = :user and date(w.createdAt) = :date")
    Optional<Weight> findByUserAndDate(@Param("date") LocalDate date, @Param("user") User user);
}

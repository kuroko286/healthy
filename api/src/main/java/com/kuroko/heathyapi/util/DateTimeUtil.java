package com.kuroko.heathyapi.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class DateTimeUtil {
    public static List<Integer> getAllDaysOfMonth(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, start.lengthOfMonth());

        return start.datesUntil(end).map(d -> d.getDayOfMonth()).toList();
    }

    public static long timeFromNowToLastDayOfMonth(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDateTime endOfMonth = LocalDateTime.of(year, month, start.lengthOfMonth(), 23, 59, 59);
        LocalDateTime now = LocalDateTime.now();

        return endOfMonth.toEpochSecond(ZoneOffset.UTC) - now.toEpochSecond(ZoneOffset.UTC);
    }
}

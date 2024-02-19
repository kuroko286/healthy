package com.kuroko.heathyapi.util;

import java.time.LocalDate;
import java.util.List;

public class DateTimeUtil {
    public static List<Integer> getAllDaysOfMonth(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, start.lengthOfMonth());

        return start.datesUntil(end).map(d -> d.getDayOfMonth()).toList();
    }
}

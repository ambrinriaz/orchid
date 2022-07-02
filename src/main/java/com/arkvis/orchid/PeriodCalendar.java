package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PeriodCalendar {

    private final Map<LocalDate, Day> dayMap;

    public PeriodCalendar() {
        dayMap = new HashMap<>();
    }

    public Day getDay(LocalDate date) {
        return dayMap.getOrDefault(date, new Day(date));
    }

    public void addMenstruation(LocalDate date) {
        Day day = dayMap.getOrDefault(date, new Day(date));
        day.addMenstruation();
        dayMap.put(date, day);
    }
}

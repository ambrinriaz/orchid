package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

public class PeriodCalendar {
    private final PeriodPredictor periodPredictor;
    private final SortedMap<LocalDate, Day> dayMap;

    public PeriodCalendar(PeriodPredictor periodPredictor) {
        this.periodPredictor = periodPredictor;
        dayMap = new TreeMap<>();
    }

    public Day getDay(LocalDate date) {
        return dayMap.getOrDefault(date, new Day(date));
    }

    public void addPeriod(LocalDate date) {
        Day day = dayMap.getOrDefault(date, new Day(date));
        day.addPeriod();
        dayMap.put(date, day);
    }

    public LocalDate getNextPeriodDate() {
        return periodPredictor.predictNextPeriodDate(dayMap.values());
    }
}

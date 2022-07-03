package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class PeriodCalendar {

    static final int DEFAULT_CYCLE_LENGTH_IN_DAYS = 28;

    private final SortedMap<LocalDate, Day> dayMap;

    public PeriodCalendar() {
        dayMap = new TreeMap<>();
    }

    public Day getDay(LocalDate date) {
        return dayMap.getOrDefault(date, new Day(date));
    }

    public void addMenstruation(LocalDate date) {
        Day day = dayMap.getOrDefault(date, new Day(date));
        day.addMenstruation();
        dayMap.put(date, day);
    }

    public LocalDate getNextMenstruationDate() {
        List<Day> cycleStartDays = getCycleStartDays();
        if (cycleStartDays.isEmpty()) return null;

        Day lastCycleStartDay = getLastItem(cycleStartDays);
        long averageCycleLength = getAverageCycleLength(cycleStartDays);
        return lastCycleStartDay.getDate().plusDays(averageCycleLength);
    }

    private long getAverageCycleLength(List<Day> cycleStartDays) {
        if (cycleStartDays.size() <= 1) return DEFAULT_CYCLE_LENGTH_IN_DAYS;

        LocalDate startDate = cycleStartDays.get(0).getDate();
        LocalDate endDate = getLastItem(cycleStartDays).getDate();

        long totalDays = DAYS.between(startDate, endDate) + DEFAULT_CYCLE_LENGTH_IN_DAYS;
        return totalDays / cycleStartDays.size();
    }

    private List<Day> getCycleStartDays() {
        List<Day> startDays = new ArrayList<>();
        LocalDate lastMenstruationDate = LocalDate.MIN;

        for (Day day : dayMap.values()) {
            LocalDate date = day.getDate();

            if (Objects.nonNull(day.getMenstruation())) {
                if (!date.equals(lastMenstruationDate.plusDays(1))) {
                    startDays.add(day);
                }
                lastMenstruationDate = date;
            }
        }
        return startDays;
    }

    private <T> T getLastItem(List<T> list) {
        return list.get(list.size() - 1);
    }
}

package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

class PeriodPredictor {

    static final int DEFAULT_CYCLE_LENGTH_IN_DAYS = 28;

    LocalDate predictNextPeriodDate(Collection<Day> allDays) {
        List<Day> cycleStartDays = getCycleStartDays(allDays);
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

    private List<Day> getCycleStartDays(Collection<Day> allDays) {
        List<Day> startDays = new ArrayList<>();
        LocalDate lastPeriodDate = LocalDate.MIN;

        for (Day day : allDays) {
            LocalDate date = day.getDate();

            if (Objects.nonNull(day.getPeriod())) {
                if (!date.equals(lastPeriodDate.plusDays(1))) {
                    startDays.add(day);
                }
                lastPeriodDate = date;
            }
        }
        return startDays;
    }

    private <T> T getLastItem(List<T> list) {
        return list.get(list.size() - 1);
    }
}

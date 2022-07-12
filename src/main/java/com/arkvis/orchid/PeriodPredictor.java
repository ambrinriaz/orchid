package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

class PeriodPredictor {

    static final int DEFAULT_CYCLE_LENGTH_IN_DAYS = 28;

    PeriodWindow predictNextPeriodWindow(Collection<Day> allDays) {
        LocalDate periodStartDate = getNextPeriodStartDate(allDays);
        if (Objects.isNull(periodStartDate)) {
            return new PeriodWindow(Collections.emptyList());
        }

        List<LocalDate> dates = new ArrayList<>();
        dates.add(periodStartDate);

        int averagePeriodLength = getAveragePeriodLength(allDays);
        for (int i = 1; i < averagePeriodLength; i++) {
            dates.add(periodStartDate.plusDays(i));
        }
        return new PeriodWindow(dates);
    }

    private int getAveragePeriodLength(Collection<Day> allDays) {
        List<Day> cycleStartDays = getCycleStartDays(allDays);
        if (cycleStartDays.isEmpty()) return 0;

        int totalPeriodDays = 0;

        for (Day day : allDays) {
            if (Objects.nonNull(day.getPeriod())) {
                totalPeriodDays++;
            }
        }

        int totalNumOfPeriods = cycleStartDays.size();
        return totalPeriodDays / totalNumOfPeriods;

    }

    private LocalDate getNextPeriodStartDate(Collection<Day> allDays) {
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

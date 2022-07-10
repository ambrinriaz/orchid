package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

public class PeriodCalendar {
    private final PeriodPredictor periodPredictor;
    private final SortedMap<LocalDate, Day> dayMap;
    private final OvulationPredictor ovulationPredictor;

    public PeriodCalendar(PeriodPredictor periodPredictor, OvulationPredictor ovulationPredictor) {
        this.periodPredictor = periodPredictor;
        this.ovulationPredictor = ovulationPredictor;
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

    public void addPeriod(LocalDate date, Flow flow) {
        addPeriod(date);
        dayMap.get(date).addPeriod(flow);
    }

    public LocalDate getNextPeriodDate() {
        return periodPredictor.predictNextPeriodDate(dayMap.values());
    }

    public LocalDate getNextOvulationDate() {
        LocalDate nextPeriodDate = getNextPeriodDate();
        return ovulationPredictor.predictNextOvulationDate(nextPeriodDate);
    }
}

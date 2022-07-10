package com.arkvis.orchid;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PeriodPredictionTest {

    @Test
    void should_returnNullDate_when_gettingNextPeriodDateOnEmptyCalendar() {
        LocalDate nextPeriod = new PeriodCalendar(new PeriodPredictor()).getNextPeriodDate();
        assertNull(nextPeriod);
    }

    @Test
    void should_returnCorrectDate_when_gettingNextPeriodDateOnCalendarWithSinglePeriod() {
        LocalDate lastPeriod = LocalDate.now();
        PeriodCalendar periodCalendar = new PeriodCalendar(new PeriodPredictor());
        periodCalendar.addPeriod(lastPeriod);

        LocalDate expectedDate = lastPeriod.plusDays(PeriodPredictor.DEFAULT_CYCLE_LENGTH_IN_DAYS);
        LocalDate nextPeriod = periodCalendar.getNextPeriodDate();
        assertEquals(expectedDate, nextPeriod);
    }

    @Test
    void should_returnCorrectDate_when_gettingNextPeriodDateOnCalendarWithSingleCycle() {
        LocalDate firstPeriod = LocalDate.now();
        PeriodCalendar periodCalendar = new PeriodCalendar(new PeriodPredictor());

        periodCalendar.addPeriod(firstPeriod);
        periodCalendar.addPeriod(firstPeriod.plusDays(1));
        periodCalendar.addPeriod(firstPeriod.plusDays(2));
        periodCalendar.addPeriod(firstPeriod.plusDays(3));

        LocalDate expectedDate = firstPeriod.plusDays(PeriodPredictor.DEFAULT_CYCLE_LENGTH_IN_DAYS);
        LocalDate nextPeriod = periodCalendar.getNextPeriodDate();
        assertEquals(expectedDate, nextPeriod);
    }

    @Test
    void should_returnCorrectDate_when_gettingNextPeriodDateOnCalendarWithTwoCycles() {
        PeriodCalendar periodCalendar = new PeriodCalendar(new PeriodPredictor());

        LocalDate firstCycleDate = LocalDate.now();
        periodCalendar.addPeriod(firstCycleDate);
        periodCalendar.addPeriod(firstCycleDate.plusDays(1));
        periodCalendar.addPeriod(firstCycleDate.plusDays(2));
        periodCalendar.addPeriod(firstCycleDate.plusDays(3));

        int firstCycleLengthInDay = 30;

        LocalDate secondCycleDate = firstCycleDate.plusDays(firstCycleLengthInDay);
        periodCalendar.addPeriod(secondCycleDate);
        periodCalendar.addPeriod(secondCycleDate.plusDays(1));
        periodCalendar.addPeriod(secondCycleDate.plusDays(2));
        periodCalendar.addPeriod(secondCycleDate.plusDays(3));

        long averageCycleLength = (PeriodPredictor.DEFAULT_CYCLE_LENGTH_IN_DAYS + firstCycleLengthInDay) / 2;
        LocalDate expectedDate = secondCycleDate.plusDays(averageCycleLength);
        LocalDate nextPeriod = periodCalendar.getNextPeriodDate();
        assertEquals(expectedDate, nextPeriod);
    }

    @Test
    void should_returnCorrectDate_when_gettingNextPeriodDateOnCalendarWithMultipleCycles() {
        PeriodCalendar periodCalendar = new PeriodCalendar(new PeriodPredictor());

        LocalDate firstCycleDate = LocalDate.now();
        periodCalendar.addPeriod(firstCycleDate);
        periodCalendar.addPeriod(firstCycleDate.plusDays(1));
        periodCalendar.addPeriod(firstCycleDate.plusDays(2));
        periodCalendar.addPeriod(firstCycleDate.plusDays(3));

        int firstCycleLengthInDay = 30;

        LocalDate secondCycleDate = firstCycleDate.plusDays(firstCycleLengthInDay);
        periodCalendar.addPeriod(secondCycleDate);
        periodCalendar.addPeriod(secondCycleDate.plusDays(1));
        periodCalendar.addPeriod(secondCycleDate.plusDays(2));
        periodCalendar.addPeriod(secondCycleDate.plusDays(3));
        periodCalendar.addPeriod(secondCycleDate.plusDays(4));
        periodCalendar.addPeriod(secondCycleDate.plusDays(5));

        int secondCycleLengthInDays = 23;

        LocalDate thirdCycleDate = secondCycleDate.plusDays(secondCycleLengthInDays);
        periodCalendar.addPeriod(thirdCycleDate);
        periodCalendar.addPeriod(thirdCycleDate.plusDays(1));
        periodCalendar.addPeriod(thirdCycleDate.plusDays(2));
        periodCalendar.addPeriod(thirdCycleDate.plusDays(3));

        long averageCycleLength = (PeriodPredictor.DEFAULT_CYCLE_LENGTH_IN_DAYS + firstCycleLengthInDay + secondCycleLengthInDays) / 3;
        LocalDate expectedDate = thirdCycleDate.plusDays(averageCycleLength);
        LocalDate nextPeriod = periodCalendar.getNextPeriodDate();
        assertEquals(expectedDate, nextPeriod);
    }
}

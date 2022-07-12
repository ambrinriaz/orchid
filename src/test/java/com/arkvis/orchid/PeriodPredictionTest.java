package com.arkvis.orchid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PeriodPredictionTest {

    private PeriodCalendar periodCalendar;

    @BeforeEach
    void setUp() {
        periodCalendar = new PeriodCalendar(new PeriodPredictor(), new OvulationPredictor());
    }

    @Test
    void should_returnEmptyPeriodWindow_when_gettingNextPeriodWindowOnEmptyCalendar() {
        PeriodWindow periodWindow = periodCalendar.getNextPeriodWindow();
        assertTrue(periodWindow.isEmpty());
    }

    @Test
    void should_returnCorrectPeriodWindow_when_gettingNextPeriodWindowOnCalendarWithSinglePeriod() {
        LocalDate lastPeriod = LocalDate.now();
        periodCalendar.addPeriod(lastPeriod);

        LocalDate expectedDate = lastPeriod.plusDays(PeriodPredictor.DEFAULT_CYCLE_LENGTH_IN_DAYS);
        List<LocalDate> dates = periodCalendar.getNextPeriodWindow().getDates();

        assertEquals(1, dates.size());
        assertCorrectDates(expectedDate, dates);
    }

    @Test
    void should_returnCorrectPeriodWindow_when_gettingNextPeriodWindowOnCalendarWithSingleCycle() {
        LocalDate firstPeriod = LocalDate.now();
        periodCalendar.addPeriod(firstPeriod);
        periodCalendar.addPeriod(firstPeriod.plusDays(1));
        periodCalendar.addPeriod(firstPeriod.plusDays(2));
        periodCalendar.addPeriod(firstPeriod.plusDays(3));

        List<LocalDate> dates = periodCalendar.getNextPeriodWindow().getDates();
        assertEquals(4, dates.size());

        LocalDate firstExpectedDate = firstPeriod.plusDays(PeriodPredictor.DEFAULT_CYCLE_LENGTH_IN_DAYS);
        assertCorrectDates(firstExpectedDate, dates);
    }

    @Test
    void should_returnCorrectPeriodWindow_when_gettingNextPeriodPeriodWindowOnCalendarWithTwoCycles() {
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

        List<LocalDate> dates = periodCalendar.getNextPeriodWindow().getDates();
        assertEquals(5, dates.size());

        long averageCycleLength = (PeriodPredictor.DEFAULT_CYCLE_LENGTH_IN_DAYS + firstCycleLengthInDay) / 2;
        LocalDate firstExpectedDate = secondCycleDate.plusDays(averageCycleLength);
        assertCorrectDates(firstExpectedDate, dates);
    }

    @Test
    void should_returnCorrectDate_when_gettingNextPeriodDateOnCalendarWithMultipleCycles() {
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

        List<LocalDate> dates = periodCalendar.getNextPeriodWindow().getDates();
        assertEquals(4, dates.size());

        long averageCycleLength = (PeriodPredictor.DEFAULT_CYCLE_LENGTH_IN_DAYS + firstCycleLengthInDay + secondCycleLengthInDays) / 3;
        LocalDate firstExpectedDate = thirdCycleDate.plusDays(averageCycleLength);
        assertCorrectDates(firstExpectedDate, dates);
    }

    private void assertCorrectDates(LocalDate firstDate, List<LocalDate> dates) {
        for (int i = 0; i < dates.size(); i++) {
            assertEquals(firstDate.plusDays(i), dates.get(i));
        }
    }
}

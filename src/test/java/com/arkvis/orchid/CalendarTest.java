package com.arkvis.orchid;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CalendarTest {

    @Test
    void should_returnDayWithNoMenstruation_when_menstruationIsMissing() {
        Day day = new PeriodCalendar().getDay(LocalDate.now());
        assertNull(day.getMenstruation());
    }

    @Test
    void should_returnDayWithMenstruation_when_menstruationIsPresent() {
        LocalDate date = LocalDate.now();

        PeriodCalendar calendar = new PeriodCalendar();
        calendar.addMenstruation(date);

        Day retrievedDay = calendar.getDay(date);
        assertNotNull(retrievedDay.getMenstruation());
    }

    @Test
    void should_returnMultipleDaysWithMenstruation_when_multipleDaysAreAddedToCalendar() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);
        LocalDate date3 = date1.plusDays(2);

        PeriodCalendar calendar = new PeriodCalendar();
        calendar.addMenstruation(date1);
        calendar.addMenstruation(date2);
        calendar.addMenstruation(date3);

        Day day1 = calendar.getDay(date1);
        Day day2 = calendar.getDay(date2);
        Day day3 = calendar.getDay(date3);

        assertNotNull(day1.getMenstruation());
        assertNotNull(day2.getMenstruation());
        assertNotNull(day3.getMenstruation());
    }

    @Test
    void should_returnNullDate_when_gettingNextMenstruationDateOnEmptyCalendar() {
        LocalDate nextMenstruation = new PeriodCalendar().getNextMenstruationDate();
        assertNull(nextMenstruation);
    }

    @Test
    void should_returnCorrectDate_when_gettingNextMenstruationDateOnCalendarWithSingleMenstruation() {
        LocalDate lastMenstruation = LocalDate.now();
        PeriodCalendar periodCalendar = new PeriodCalendar();
        periodCalendar.addMenstruation(lastMenstruation);

        LocalDate expectedDate = lastMenstruation.plusDays(PeriodCalendar.DEFAULT_CYCLE_LENGTH_IN_DAYS);
        LocalDate nextMenstruation = periodCalendar.getNextMenstruationDate();
        assertEquals(expectedDate, nextMenstruation);
    }

    @Test
    void should_returnCorrectDate_when_gettingNextMenstruationDateOnCalendarWithSingleCycle() {
        LocalDate firstMenstruation = LocalDate.now();
        PeriodCalendar periodCalendar = new PeriodCalendar();

        periodCalendar.addMenstruation(firstMenstruation);
        periodCalendar.addMenstruation(firstMenstruation.plusDays(1));
        periodCalendar.addMenstruation(firstMenstruation.plusDays(2));
        periodCalendar.addMenstruation(firstMenstruation.plusDays(3));

        LocalDate expectedDate = firstMenstruation.plusDays(PeriodCalendar.DEFAULT_CYCLE_LENGTH_IN_DAYS);
        LocalDate nextMenstruation = periodCalendar.getNextMenstruationDate();
        assertEquals(expectedDate, nextMenstruation);
    }

    @Test
    void should_returnCorrectDate_when_gettingNextMenstruationDateOnCalendarWithTwoCycles() {
        PeriodCalendar periodCalendar = new PeriodCalendar();

        LocalDate firstCycleDate = LocalDate.now();
        periodCalendar.addMenstruation(firstCycleDate);
        periodCalendar.addMenstruation(firstCycleDate.plusDays(1));
        periodCalendar.addMenstruation(firstCycleDate.plusDays(2));
        periodCalendar.addMenstruation(firstCycleDate.plusDays(3));

        int firstCycleLengthInDay = 30;

        LocalDate secondCycleDate = firstCycleDate.plusDays(firstCycleLengthInDay);
        periodCalendar.addMenstruation(secondCycleDate);
        periodCalendar.addMenstruation(secondCycleDate.plusDays(1));
        periodCalendar.addMenstruation(secondCycleDate.plusDays(2));
        periodCalendar.addMenstruation(secondCycleDate.plusDays(3));

        long averageCycleLength = (PeriodCalendar.DEFAULT_CYCLE_LENGTH_IN_DAYS + firstCycleLengthInDay) / 2;
        LocalDate expectedDate = secondCycleDate.plusDays(averageCycleLength);
        LocalDate nextMenstruation = periodCalendar.getNextMenstruationDate();
        assertEquals(expectedDate, nextMenstruation);
    }

    @Test
    void should_returnCorrectDate_when_gettingNextMenstruationDateOnCalendarWithMultipleCycles() {
        PeriodCalendar periodCalendar = new PeriodCalendar();

        LocalDate firstCycleDate = LocalDate.now();
        periodCalendar.addMenstruation(firstCycleDate);
        periodCalendar.addMenstruation(firstCycleDate.plusDays(1));
        periodCalendar.addMenstruation(firstCycleDate.plusDays(2));
        periodCalendar.addMenstruation(firstCycleDate.plusDays(3));

        int firstCycleLengthInDay = 30;

        LocalDate secondCycleDate = firstCycleDate.plusDays(firstCycleLengthInDay);
        periodCalendar.addMenstruation(secondCycleDate);
        periodCalendar.addMenstruation(secondCycleDate.plusDays(1));
        periodCalendar.addMenstruation(secondCycleDate.plusDays(2));
        periodCalendar.addMenstruation(secondCycleDate.plusDays(3));
        periodCalendar.addMenstruation(secondCycleDate.plusDays(4));
        periodCalendar.addMenstruation(secondCycleDate.plusDays(5));

        int secondCycleLengthInDays = 23;

        LocalDate thirdCycleDate = secondCycleDate.plusDays(secondCycleLengthInDays);
        periodCalendar.addMenstruation(thirdCycleDate);
        periodCalendar.addMenstruation(thirdCycleDate.plusDays(1));
        periodCalendar.addMenstruation(thirdCycleDate.plusDays(2));
        periodCalendar.addMenstruation(thirdCycleDate.plusDays(3));

        long averageCycleLength = (PeriodCalendar.DEFAULT_CYCLE_LENGTH_IN_DAYS + firstCycleLengthInDay + secondCycleLengthInDays) / 3;
        LocalDate expectedDate = thirdCycleDate.plusDays(averageCycleLength);
        LocalDate nextMenstruation = periodCalendar.getNextMenstruationDate();
        assertEquals(expectedDate, nextMenstruation);
    }
}

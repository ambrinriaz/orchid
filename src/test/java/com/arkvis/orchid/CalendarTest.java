package com.arkvis.orchid;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CalendarTest {

    @Test
    void should_returnDayWithNoPeriod_when_periodIsMissing() {
        Day day = new PeriodCalendar(new PeriodPredictor()).getDay(LocalDate.now());
        assertNull(day.getPeriod());
    }

    @Test
    void should_returnDayWithPeriod_when_periodIsPresent() {
        LocalDate date = LocalDate.now();

        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
        calendar.addPeriod(date);

        Day retrievedDay = calendar.getDay(date);
        assertNotNull(retrievedDay.getPeriod());
    }

    @Test
    void should_returnMultipleDaysWithPeriod_when_multipleDaysAreAddedToCalendar() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);
        LocalDate date3 = date1.plusDays(2);

        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
        calendar.addPeriod(date1);
        calendar.addPeriod(date2);
        calendar.addPeriod(date3);

        Day day1 = calendar.getDay(date1);
        Day day2 = calendar.getDay(date2);
        Day day3 = calendar.getDay(date3);

        assertNotNull(day1.getPeriod());
        assertNotNull(day2.getPeriod());
        assertNotNull(day3.getPeriod());
    }

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

    @Test
    void should_returnNoFlow_when_periodAddedWithNoFlow(){
        LocalDate date = LocalDate.now();

        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
        calendar.addPeriod(date);

        Day retrievedDay  = calendar.getDay(date);
        Period period =   retrievedDay.getPeriod();
        Flow flow = period.getFlow();
        assertNull(flow);
    }

    @Test
    void should_returnCorrectFlow_when_PeriodAddedWithLightFlow(){
        LocalDate date = LocalDate.now();
        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
        calendar.addPeriod(date, Flow.LIGHT);

        Day retrievedDay  = calendar.getDay(date);
        Period retrievedPeriod =   retrievedDay.getPeriod();

        assertEquals(Flow.LIGHT, retrievedPeriod.getFlow());
    }

    @Test
    void should_returnCorrectFlow_when_PeriodAddedWithMediumFlow(){
        LocalDate date = LocalDate.now();
        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
        calendar.addPeriod(date, Flow.MEDIUM);

        Day retrievedDay  = calendar.getDay(date);
        Period retrievedPeriod =   retrievedDay.getPeriod();

        assertEquals(Flow.MEDIUM, retrievedPeriod.getFlow());
    }

    @Test
    void should_returnCorrectFlow_when_PeriodAddedWithHeavyFlow(){
        LocalDate date = LocalDate.now();
        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
        calendar.addPeriod(date, Flow.HEAVY);

        Day retrievedDay  = calendar.getDay(date);
        Period retrievedPeriod =   retrievedDay.getPeriod();

        assertEquals(Flow.HEAVY, retrievedPeriod.getFlow());
    }

    @Test
    void should_returnCorrectFlow_when_PeriodAddedWithSpottingFlow(){
        LocalDate date = LocalDate.now();
        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
        calendar.addPeriod(date, Flow.SPOTTING);

        Day retrievedDay  = calendar.getDay(date);
        Period retrievedPeriod =   retrievedDay.getPeriod();

        assertEquals(Flow.SPOTTING, retrievedPeriod.getFlow());
    }
}

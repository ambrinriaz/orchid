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
    void should_returnCorrectFlow_when_periodAddedWithLightFlow(){
        LocalDate date = LocalDate.now();
        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
        calendar.addPeriod(date, Flow.LIGHT);

        Day retrievedDay  = calendar.getDay(date);
        Period retrievedPeriod =   retrievedDay.getPeriod();

        assertEquals(Flow.LIGHT, retrievedPeriod.getFlow());
    }

    @Test
    void should_returnCorrectFlow_when_periodAddedWithMediumFlow(){
        LocalDate date = LocalDate.now();
        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
        calendar.addPeriod(date, Flow.MEDIUM);

        Day retrievedDay  = calendar.getDay(date);
        Period retrievedPeriod =   retrievedDay.getPeriod();

        assertEquals(Flow.MEDIUM, retrievedPeriod.getFlow());
    }

    @Test
    void should_returnCorrectFlow_when_periodAddedWithHeavyFlow(){
        LocalDate date = LocalDate.now();
        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
        calendar.addPeriod(date, Flow.HEAVY);

        Day retrievedDay  = calendar.getDay(date);
        Period retrievedPeriod =   retrievedDay.getPeriod();

        assertEquals(Flow.HEAVY, retrievedPeriod.getFlow());
    }

    @Test
    void should_returnCorrectFlow_when_periodAddedWithSpottingFlow(){
        LocalDate date = LocalDate.now();
        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
        calendar.addPeriod(date, Flow.SPOTTING);

        Day retrievedDay  = calendar.getDay(date);
        Period retrievedPeriod =   retrievedDay.getPeriod();

        assertEquals(Flow.SPOTTING, retrievedPeriod.getFlow());
    }
}

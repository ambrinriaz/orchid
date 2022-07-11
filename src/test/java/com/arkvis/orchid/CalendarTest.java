package com.arkvis.orchid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CalendarTest {

    private PeriodCalendar periodCalendar;

    @BeforeEach
    void setUp() {
        periodCalendar = new PeriodCalendar(new PeriodPredictor(), new OvulationPredictor());
    }

    @Test
    void should_returnDayWithNoPeriod_when_periodIsMissing() {
        Day day = periodCalendar.getDay(LocalDate.now());
        assertNull(day.getPeriod());
    }

    @Test
    void should_returnDayWithPeriod_when_periodIsPresent() {
        LocalDate date = LocalDate.now();
        periodCalendar.addPeriod(date);

        Day retrievedDay = periodCalendar.getDay(date);
        assertNotNull(retrievedDay.getPeriod());
    }

    @Test
    void should_returnMultipleDaysWithPeriod_when_multipleDaysAreAddedToCalendar() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);
        LocalDate date3 = date1.plusDays(2);

        periodCalendar.addPeriod(date1);
        periodCalendar.addPeriod(date2);
        periodCalendar.addPeriod(date3);

        Day day1 = periodCalendar.getDay(date1);
        Day day2 = periodCalendar.getDay(date2);
        Day day3 = periodCalendar.getDay(date3);

        assertNotNull(day1.getPeriod());
        assertNotNull(day2.getPeriod());
        assertNotNull(day3.getPeriod());
    }

    @Test
    void should_returnNoFlow_when_periodAddedWithNoFlow() {
        LocalDate date = LocalDate.now();
        periodCalendar.addPeriod(date);

        Day retrievedDay = periodCalendar.getDay(date);
        Period period = retrievedDay.getPeriod();
        Flow flow = period.getFlow();
        assertNull(flow);
    }

    @Test
    void should_returnCorrectFlow_when_periodAddedWithLightFlow() {
        LocalDate date = LocalDate.now();
        periodCalendar.addPeriod(date, Flow.LIGHT);

        Day retrievedDay = periodCalendar.getDay(date);
        Period retrievedPeriod = retrievedDay.getPeriod();

        assertEquals(Flow.LIGHT, retrievedPeriod.getFlow());
    }

    @Test
    void should_returnCorrectFlow_when_periodAddedWithMediumFlow() {
        LocalDate date = LocalDate.now();
        periodCalendar.addPeriod(date, Flow.MEDIUM);

        Day retrievedDay = periodCalendar.getDay(date);
        Period retrievedPeriod = retrievedDay.getPeriod();

        assertEquals(Flow.MEDIUM, retrievedPeriod.getFlow());
    }

    @Test
    void should_returnCorrectFlow_when_periodAddedWithHeavyFlow() {
        LocalDate date = LocalDate.now();
        periodCalendar.addPeriod(date, Flow.HEAVY);

        Day retrievedDay = periodCalendar.getDay(date);
        Period retrievedPeriod = retrievedDay.getPeriod();

        assertEquals(Flow.HEAVY, retrievedPeriod.getFlow());
    }

    @Test
    void should_returnCorrectFlow_when_periodAddedWithSpottingFlow() {
        LocalDate date = LocalDate.now();
        periodCalendar.addPeriod(date, Flow.SPOTTING);

        Day retrievedDay = periodCalendar.getDay(date);
        Period retrievedPeriod = retrievedDay.getPeriod();
        assertEquals(Flow.SPOTTING, retrievedPeriod.getFlow());
    }

    @Test
    void should_returnTemperature_when_temperatureAddedToDay(){
        LocalDate date = LocalDate.now();
        Temperature temperature = new Temperature(new BigDecimal("100"), Metric.FAHRENHEIT);
        periodCalendar.addTemperature(date, temperature);

        Day retrievedDay = periodCalendar.getDay(date);
        Temperature retrievedTemp = retrievedDay.getTemperature();

        assertEquals(temperature.getValue(), retrievedTemp.getValue());
        assertEquals(temperature.getMetric(), retrievedTemp.getMetric());
    }

    @Test
    void should_returnNoTemperature_when_noTemperatureAddedToDay(){
        LocalDate date = LocalDate.now();
        Day retrievedDay = periodCalendar.getDay(date);

        Temperature retrievedTemp = retrievedDay.getTemperature();
        assertNull(retrievedTemp);
    }

    @Test
    void should_returnTemperature_when_temperatureAndPeriodAddedToDay(){
        LocalDate date = LocalDate.now();
        Temperature temperature = new Temperature(new BigDecimal("100"), Metric.FAHRENHEIT);

        periodCalendar.addTemperature(date, temperature);
        periodCalendar.addPeriod(date, Flow.HEAVY);


        Day retrievedDay = periodCalendar.getDay(date);
        Temperature retrievedTemp = retrievedDay.getTemperature();
        Period retrievedPeriod = retrievedDay.getPeriod();

        assertEquals(temperature.getValue(), retrievedTemp.getValue());
        assertEquals(temperature.getMetric(), retrievedTemp.getMetric());
        assertEquals(Flow.HEAVY, retrievedPeriod.getFlow());
    }
}

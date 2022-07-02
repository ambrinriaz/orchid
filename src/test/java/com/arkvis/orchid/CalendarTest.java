package com.arkvis.orchid;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}

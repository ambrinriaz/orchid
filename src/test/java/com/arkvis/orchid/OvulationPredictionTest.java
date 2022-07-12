package com.arkvis.orchid;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OvulationPredictionTest {

    @Test
    void should_returnNullDate_when_gettingNextOvulationDateOnEmptyCalendar() {
        LocalDate nextOvulation = new PeriodCalendar(new PeriodPredictor(), new OvulationPredictor()).getNextOvulationDate();
        assertNull(nextOvulation);
    }

    @Test
    void should_returnCorrectDate_when_gettingNextOvulationDateOnCalendarThatHasPeriod() {
        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor(), new OvulationPredictor());
        calendar.addPeriod(LocalDate.now());

        LocalDate nextPeriodDate = calendar.getNextPeriodWindow().getStartDate();
        LocalDate nextOvulationDate = calendar.getNextOvulationDate();
        assertEquals(nextPeriodDate.minusDays(OvulationPredictor.DEFAULT_DAYS_BEFORE_PERIOD), nextOvulationDate);
    }
}

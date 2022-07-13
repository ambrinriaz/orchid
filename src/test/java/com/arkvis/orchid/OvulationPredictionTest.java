package com.arkvis.orchid;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class OvulationPredictionTest {

    @Test
    void should_returnCorrectWindow_when_gettingNextFertilityWindowOnCalendarThatHasPeriod() {
        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor(), new OvulationPredictor());
        calendar.addPeriod(LocalDate.now());

        LocalDate nextPeriodDate = calendar.getNextPeriodWindow().getStartDate();
        LocalDate nextOvulationDate = nextPeriodDate.minusDays(OvulationPredictor.DEFAULT_DAYS_BEFORE_PERIOD);
        LocalDate ovulationStartDay = nextOvulationDate.minusDays(5);
        List<LocalDate> ovulationWindow = new ArrayList<>();

        IntStream.rangeClosed(0, OvulationPredictor.DEFAULT_DAYS_BEFORE_OVULATION + 1)
                .forEach(i -> ovulationWindow.add(ovulationStartDay.plusDays(i)));

        FertilityWindow nextFertilityWindow = calendar.getNextFertilityWindow();

        assertEquals(ovulationWindow, nextFertilityWindow.getDates());
        assertEquals(nextOvulationDate, nextFertilityWindow.getOvulationDate());
    }

    @Test
    void should_returnEmptyFertilityWindow_when_gettingNextFertilityWindowOnEmptyCalendar() {
        PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor(), new OvulationPredictor());
        FertilityWindow fertilityWindow = calendar.getNextFertilityWindow();
        assertTrue(fertilityWindow.isEmpty());
    }
}
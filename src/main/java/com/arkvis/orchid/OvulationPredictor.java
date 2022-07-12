package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

class OvulationPredictor {

    static final int DEFAULT_DAYS_BEFORE_PERIOD = 14;

    static final int DEFAULT_DAYS_BEFORE_OVULATION = 5;

    private final List<LocalDate> nextFertilityWindow;

    OvulationPredictor() {
        nextFertilityWindow = new ArrayList<>();
    }

    FertilityWindow predictNextFertilityWindow(LocalDate nextPeriodDate) {
        if (Objects.isNull(nextPeriodDate)) return null;

        LocalDate nextOvulationDate = nextPeriodDate.minusDays(DEFAULT_DAYS_BEFORE_PERIOD);

        LocalDate ovulationStartDay = nextOvulationDate.minusDays(DEFAULT_DAYS_BEFORE_OVULATION);

        IntStream.rangeClosed(0, DEFAULT_DAYS_BEFORE_OVULATION + 1)
                .forEach(i -> nextFertilityWindow.add(ovulationStartDay.plusDays(i)));

        return new FertilityWindow(nextFertilityWindow, nextOvulationDate);
    }
}


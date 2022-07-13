package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

class OvulationPredictor {

    static final int DEFAULT_DAYS_BEFORE_PERIOD = 14;

    static final int DEFAULT_DAYS_BEFORE_OVULATION = 5;

    FertilityWindow predictNextFertilityWindow(PeriodWindow periodWindow) {
        LocalDate nextPeriodDate = periodWindow.getStartDate();

        if (Objects.isNull(nextPeriodDate)) {
            return new FertilityWindow(Collections.emptyList(), null);
        }

        LocalDate nextOvulationDate = nextPeriodDate.minusDays(DEFAULT_DAYS_BEFORE_PERIOD);
        LocalDate ovulationStartDay = nextOvulationDate.minusDays(DEFAULT_DAYS_BEFORE_OVULATION);

        List<LocalDate> nextFertilityWindow = new ArrayList<>();
        IntStream.rangeClosed(0, DEFAULT_DAYS_BEFORE_OVULATION + 1)
                .forEach(i -> nextFertilityWindow.add(ovulationStartDay.plusDays(i)));

        return new FertilityWindow(nextFertilityWindow, nextOvulationDate);
    }
}


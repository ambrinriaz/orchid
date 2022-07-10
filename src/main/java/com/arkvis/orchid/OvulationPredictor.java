package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.Objects;

class OvulationPredictor {

    static final int DEFAULT_DAYS_BEFORE_PERIOD = 14;

    LocalDate predictNextOvulationDate(LocalDate nextPeriodDate) {
        if (Objects.isNull(nextPeriodDate)) return null;
        return nextPeriodDate.minusDays(DEFAULT_DAYS_BEFORE_PERIOD);
    }
}

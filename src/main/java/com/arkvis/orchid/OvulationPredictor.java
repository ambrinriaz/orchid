package com.arkvis.orchid;

import java.time.LocalDate;

class OvulationPredictor {

    static final int DEFAULT_DAYS_BEFORE_PERIOD = 14;

    LocalDate predictNextOvulationDate(PeriodWindow periodWindow) {
        if (periodWindow.isEmpty()) return null;
        return periodWindow.getStartDate().minusDays(DEFAULT_DAYS_BEFORE_PERIOD);
    }
}

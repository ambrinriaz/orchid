package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class PeriodWindow {

    private final List<LocalDate> dates;

    PeriodWindow(List<LocalDate> dates) {
        this.dates = dates;
    }

    public LocalDate getStartDate() {
        return isEmpty() ? null : dates.get(0);
    }

    public boolean isEmpty() {
        return dates.isEmpty();
    }

    public List<LocalDate> getDates() {
        return Collections.unmodifiableList(dates);
    }
}

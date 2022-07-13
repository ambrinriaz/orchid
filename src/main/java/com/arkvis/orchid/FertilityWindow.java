package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class FertilityWindow {

    private final List<LocalDate> dates;

    private final LocalDate ovulationDate;

    FertilityWindow(List<LocalDate> dates, LocalDate ovulationDate) {
        this.dates = dates;
        this.ovulationDate = ovulationDate;
    }

    public List<LocalDate> getDates() {
        return Collections.unmodifiableList(dates);
    }

    public LocalDate getOvulationDate() {
        return ovulationDate;
    }

    public boolean isEmpty() {
        return dates.isEmpty();
    }
}

package com.arkvis.orchid;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class FertilityWindow {

    private final List<LocalDate> fertilityWindow;

    private final LocalDate ovulationDate;

    FertilityWindow(List<LocalDate> fertilityWindow, LocalDate ovulationDate) {
        this.fertilityWindow = fertilityWindow;
        this.ovulationDate = ovulationDate;
    }

    public List<LocalDate> getFertilityWindow() {
        return Collections.unmodifiableList(fertilityWindow);
    }

    public LocalDate getOvulationDate() {
        return ovulationDate;
    }
}

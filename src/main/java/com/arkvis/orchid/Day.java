package com.arkvis.orchid;

import java.time.LocalDate;

public class Day {
    private final LocalDate date;
    private Period period;
    private Temperature temperature;

    Day(LocalDate date) {
        this.date = date;
    }

    void addPeriod() {
        period = new Period();
    }

    void addPeriod(Flow flow) {
        addPeriod();
        period.setFlow(flow);
    }

    public Period getPeriod() {
        return period;
    }

    LocalDate getDate() {
        return date;
    }

    void addTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Temperature getTemperature() {
        return temperature;
    }
}

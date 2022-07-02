package com.arkvis.orchid;

import java.time.LocalDate;

public class Day {
    private final LocalDate date;
    private Menstruation menstruation;

    Day(LocalDate date) {
        this.date = date;
    }

    public Menstruation getMenstruation() {
        return menstruation;
    }

    void addMenstruation() {
        menstruation = new Menstruation();
    }

    LocalDate getDate() {
        return date;
    }
}

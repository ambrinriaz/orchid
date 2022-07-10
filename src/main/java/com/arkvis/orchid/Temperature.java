package com.arkvis.orchid;

import java.math.BigDecimal;

public class Temperature {

    private final BigDecimal value;

    private final Metric metric;

    public Temperature(BigDecimal value, Metric metric) {
        this.value = value;
        this.metric = metric;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Metric getMetric() {
        return metric;
    }
}

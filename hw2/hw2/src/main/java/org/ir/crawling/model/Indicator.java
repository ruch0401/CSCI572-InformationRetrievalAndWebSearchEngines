package org.ir.crawling.model;

public enum Indicator {
    OK("OK"),
    N_OK("N_OK");

    public final String value;

    Indicator(String indicator) {
        this.value = indicator;
    }
}
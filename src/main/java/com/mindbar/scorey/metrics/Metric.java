package com.mindbar.scorey.metrics;

/**
 * @author mishadoff
 */
public enum Metric {
    BATTERY("battery"),
    DISPLAY("display"),
    PERFORMANCE("performance")

    ;

    private String key;

    Metric(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

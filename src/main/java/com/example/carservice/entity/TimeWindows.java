package com.example.carservice.entity;

import java.sql.Time;

public enum TimeWindows {
    FIRST_WINDOW("08:00"),
    SECOND_WINDOW("11:00"),
    THIRD_WINDOW("14:00"),
    FOURTH_HOURS("17:00");

    private final String startTime;

    TimeWindows(String startTime) {
        this.startTime = startTime;
    }

    public String getHours() {
        return startTime;
    }
}

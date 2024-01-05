package com.example.carservice.entity;

import java.sql.Time;

public enum TimeWindows {
    FIRST_WINDOW("08:00:00"),
    SECOND_WINDOW("11:00:00"),
    THIRD_WINDOW("14:00:00"),
    FOURTH_HOURS("17:00:00");

    private final Time startTime;

    TimeWindows(String startTime) {
        this.startTime = Time.valueOf(startTime);
    }

    public Time getHours() {
        return startTime;
    }
}

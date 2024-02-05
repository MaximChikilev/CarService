package com.example.carservice.entity;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public static List<String> getAllStartTimes() {
        return Arrays.stream(TimeWindows.values())
                .map(TimeWindows::getHours)
                .collect(Collectors.toList());
    }
    public static TimeWindows getByHours(String hours) {
        for (TimeWindows timeWindow : values()) {
            if (timeWindow.getHours().equals(hours)) {
                return timeWindow;
            }
        }
        throw new IllegalArgumentException("No TimeWindow found for hours: " + hours);
    }
}

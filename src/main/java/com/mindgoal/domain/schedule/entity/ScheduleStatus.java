package com.mindgoal.domain.schedule.entity;

public enum ScheduleStatus {
    AVAILABLE("예약가능"),
    BOOKED("예약됨"),
    CANCELLED("취소");

    private final String description;

    ScheduleStatus(String description) {
        this.description = description;
    }
}

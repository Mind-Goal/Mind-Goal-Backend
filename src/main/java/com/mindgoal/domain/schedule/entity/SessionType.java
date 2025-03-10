package com.mindgoal.domain.schedule.entity;

import java.time.LocalDateTime;
import java.util.Arrays;

public enum SessionType {

    BASIC("BASIC", 60),
    EXPLANATION("EXPLANATION", 90),
    ADVANCED("ADVANCED", 120);

    private final String name;
    private final int durationMinutes;

    SessionType(String name, int durationMinutes) {
        this.name = name;
        this.durationMinutes = durationMinutes;
    }

    public String getName() {
        return name;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public LocalDateTime calculateEndTime(LocalDateTime startTime) {
        return startTime.plusMinutes(this.durationMinutes);
    }

    public static SessionType from(String name) {
        return Arrays.stream(SessionType.values())
                .filter(sessionType -> sessionType.name.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보입니다."));
    }
}

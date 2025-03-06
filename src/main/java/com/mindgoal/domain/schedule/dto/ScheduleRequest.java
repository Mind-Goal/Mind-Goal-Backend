package com.mindgoal.domain.schedule.dto;

import java.time.LocalDateTime;

public record ScheduleRequest(Long expertId, LocalDateTime startTime, String sessionType) {
}

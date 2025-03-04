package com.mindgoal.domain.schedule.dto;

import com.mindgoal.domain.schedule.entity.ScheduleStatus;
import java.time.LocalDateTime;

public record ScheduleResponse(Long id, String sessionType, LocalDateTime startTime, LocalDateTime endTime,
                               ScheduleStatus status) {
}

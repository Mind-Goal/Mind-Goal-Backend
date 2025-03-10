package com.mindgoal.domain.schedule.dto;

import com.mindgoal.domain.schedule.entity.Schedule;
import com.mindgoal.domain.schedule.entity.ScheduleStatus;
import java.time.LocalDateTime;

public record ScheduleResponse(Long id, String sessionType, LocalDateTime startTime, LocalDateTime endTime,
                               ScheduleStatus status) {
    public static ScheduleResponse from(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getSessionType(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getStatus()
        );
    }
}

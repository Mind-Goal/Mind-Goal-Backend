package com.mindgoal.domain.schedule.repository.query;

import java.time.LocalDateTime;

public interface ScheduleRepositoryCustom {
    boolean existSchedule(LocalDateTime startTime, LocalDateTime endTime, Long userId, Long expertId);
}

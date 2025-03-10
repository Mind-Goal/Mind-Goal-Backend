package com.mindgoal.domain.schedule.repository.query;

import com.mindgoal.domain.schedule.entity.Schedule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepositoryCustom {
    boolean existSchedule(LocalDateTime startTime, LocalDateTime endTime, Long userId, Long expertId);
    List<Schedule> findAllByDateAndUserId(LocalDate startTime, LocalDate endTime, Long userId);

    List<Schedule> findAllByDateAndExpertId(LocalDate startTime, LocalDate endTime, Long expertId);
}

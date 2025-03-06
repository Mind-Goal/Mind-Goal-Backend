package com.mindgoal.domain.schedule.service;

import com.mindgoal.domain.schedule.dto.ScheduleRequest;
import com.mindgoal.domain.schedule.dto.ScheduleResponse;
import com.mindgoal.domain.schedule.entity.Schedule;
import com.mindgoal.domain.schedule.entity.ScheduleDate;
import com.mindgoal.domain.schedule.entity.SessionType;
import com.mindgoal.domain.schedule.repository.ScheduleRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponse create(final ScheduleRequest request, final Long userId) {
        SessionType sessionType = SessionType.from(request.sessionType());
        final LocalDateTime startTime = request.startTime();
        final LocalDateTime endTime = sessionType.calculateEndTime(request.startTime());

        validateExist(startTime, endTime, userId, request.expertId());
        ScheduleDate scheduleDate = new ScheduleDate(startTime, endTime);
        Schedule schedule = new Schedule(userId, request.expertId(), sessionType.getName(), scheduleDate);

        return ScheduleResponse.from(scheduleRepository.save(schedule));
    }

    private void validateExist(final LocalDateTime startTime, final LocalDateTime endTime, final Long userId,
                               final Long expertId) {
        if (existSchedule(startTime, endTime, userId, expertId)) {
            throw new IllegalArgumentException("Schedule already exists");
        }
    }

    private boolean existSchedule(final LocalDateTime startTime, final LocalDateTime endTime, final Long userId,
                                  final Long expertId) {
        return scheduleRepository.existSchedule(startTime, endTime, userId, expertId);
    }
}

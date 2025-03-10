package com.mindgoal.domain.schedule.service;

import com.mindgoal.domain.expert.repository.ExpertRepository;
import com.mindgoal.domain.schedule.dto.ScheduleRequest;
import com.mindgoal.domain.schedule.dto.ScheduleResponse;
import com.mindgoal.domain.schedule.entity.Schedule;
import com.mindgoal.domain.schedule.entity.ScheduleDate;
import com.mindgoal.domain.schedule.entity.SessionType;
import com.mindgoal.domain.schedule.repository.ScheduleRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ExpertRepository expertRepository;

    public ScheduleResponse create(final ScheduleRequest request, final Long userId) {
        final SessionType sessionType = SessionType.from(request.sessionType());
        final LocalDateTime startTime = request.startTime();
        final LocalDateTime endTime = sessionType.calculateEndTime(request.startTime());

        validateExist(startTime, endTime, userId, request.expertId());
        final ScheduleDate scheduleDate = new ScheduleDate(startTime, endTime);
        final Schedule schedule = new Schedule(userId, request.expertId(), sessionType.getName(), scheduleDate);

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

    public List<ScheduleResponse> findMySchedules(final LocalDate startTime, final LocalDate endTime,
                                                  final Long userId) {
        final List<Schedule> schedules = findScheduleByUserType(startTime, endTime, userId);
        return schedules.stream()
                .map(ScheduleResponse::from)
                .toList();
    }

    private List<Schedule> findScheduleByUserType(final LocalDate startTime, final LocalDate endTime,
                                                  final Long userId) {
        if (expertRepository.existsByUserId(userId)) {
            final Long expertId = expertRepository.findExpertByUserId(userId).getId();
            return scheduleRepository.findAllByDateAndExpertId(startTime, endTime, expertId);
        }
        return scheduleRepository.findAllByDateAndUserId(startTime, endTime, userId);
    }
}

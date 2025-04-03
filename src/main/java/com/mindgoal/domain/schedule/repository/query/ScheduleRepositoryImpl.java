package com.mindgoal.domain.schedule.repository.query;


import static com.mindgoal.domain.schedule.entity.QSchedule.schedule;

import com.mindgoal.domain.schedule.entity.QSchedule;
import com.mindgoal.domain.schedule.entity.Schedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existSchedule(LocalDateTime startTime, LocalDateTime endTime, Long userId, Long expertId) {
        QSchedule schedule = QSchedule.schedule;

        // userId로 스케줄 조회
        boolean userHasSchedule = queryFactory
                .selectOne()
                .from(schedule)
                .where(schedule.userId.eq(userId)
                        .and(schedule.scheduleDate.startDate.lt(endTime))
                        .and(schedule.scheduleDate.endDate.gt(startTime))
                )
                .fetchFirst() != null;

        // expertId로 스케줄 조회
        boolean expertHasSchedule = queryFactory
                .selectOne()
                .from(schedule)
                .where(schedule.expertId.eq(expertId)
                        .and(schedule.scheduleDate.startDate.lt(endTime))
                        .and(schedule.scheduleDate.endDate.gt(startTime))
                )
                .fetchFirst() != null;

        return userHasSchedule || expertHasSchedule;
    }

    @Override
    public List<Schedule> findAllByDateAndUserId(LocalDate startTime, LocalDate endTime, Long userId) {
        return queryFactory
                .selectFrom(schedule)
                .where(
                        schedule.scheduleDate.startDate.between(startTime.atStartOfDay(), endTime.atTime(23, 59, 59)),
                        schedule.userId.eq(userId)
                )
                .fetch();
    }

    @Override
    public List<Schedule> findAllByDateAndExpertId(LocalDate startTime, LocalDate endTime, Long expertId) {
        return queryFactory
                .selectFrom(schedule)
                .where(
                        schedule.scheduleDate.startDate.between(startTime.atStartOfDay(), endTime.atTime(23, 59, 59)),
                        schedule.expertId.eq(expertId)
                )
                .fetch();
    }
}

package com.mindgoal.domain.schedule.repository.query;


import com.mindgoal.domain.schedule.entity.QSchedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
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
                        .and(schedule.scheduleDate.startDate.lt(endTime))  // 예약 종료 시간이 startTime보다 뒤여야 함
                        .and(schedule.scheduleDate.endDate.gt(startTime))  // 예약 시작 시간이 endTime보다 앞이어야 함
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
}

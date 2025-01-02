package com.mindgoal.domain.schedule.entity;

import com.mindgoal.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SCHEDULES")
@Entity
@Getter
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "SESSION_COUNT")
    private int sessionCount;

    @Column(name = "SESSION_TYPE")
    private String sessionType;

    @Embedded
    private ScheduleDate scheduleDate;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    public Schedule(int sessionCount, String sessionType, ScheduleDate scheduleDate, ScheduleStatus status) {
        this.sessionCount = sessionCount;
        this.sessionType = sessionType;
        this.scheduleDate = scheduleDate;
        this.status = status;
    }
}

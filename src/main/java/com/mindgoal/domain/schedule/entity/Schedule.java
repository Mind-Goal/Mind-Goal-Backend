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
import java.time.LocalDateTime;
import java.util.Objects;
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

    @Column
    private Long userId;

    @Column
    private Long expertId;

    @Column(name = "SESSION_TYPE")
    private String sessionType;

    @Embedded
    private ScheduleDate scheduleDate;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    public Schedule(Long userId, Long expertId, String sessionType, ScheduleDate scheduleDate, ScheduleStatus status) {
        this.userId = userId;
        this.expertId = expertId;
        this.sessionType = sessionType;
        this.scheduleDate = scheduleDate;
        this.status = status;
    }

    public Schedule(Long userId, Long expertId, String sessionType, ScheduleDate scheduleDate) {
        this.userId = userId;
        this.expertId = expertId;
        this.sessionType = sessionType;
        this.scheduleDate = scheduleDate;
        this.status = ScheduleStatus.BOOKED;
    }

    public LocalDateTime getStartTime() {
        return this.scheduleDate.getStartDate();
    }

    public LocalDateTime getEndTime() {
        return this.scheduleDate.getStartDate();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Schedule schedule = (Schedule) object;
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

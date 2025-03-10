package com.mindgoal.domain.schedule.repository;

import com.mindgoal.domain.schedule.entity.Schedule;
import com.mindgoal.domain.schedule.repository.query.ScheduleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {
}

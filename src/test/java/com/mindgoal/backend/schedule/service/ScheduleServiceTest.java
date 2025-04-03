package com.mindgoal.backend.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mindgoal.backend.support.annotation.ServiceTest;
import com.mindgoal.backend.support.fixture.expert.ExpertFixture;
import com.mindgoal.backend.support.fixture.user.UserFixture;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.schedule.dto.ScheduleRequest;
import com.mindgoal.domain.schedule.repository.ScheduleRepository;
import com.mindgoal.domain.schedule.service.ScheduleService;
import com.mindgoal.domain.user.entity.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
public class ScheduleServiceTest {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Test
    void 일정_생성() {
        User user = UserFixture.오션();
        Expert expert = ExpertFixture.진로_전문가();

        ScheduleRequest request = new ScheduleRequest(expert.getId(), LocalDateTime.of(2025, 4, 6, 14, 0, 0), "BASIC");

        assertThat(scheduleService.create(request, user.getId())).isNotNull();
    }

    @Test
    void 일정이_있는_경우_예외처리() {
        User ocean = UserFixture.오션();
        User step = UserFixture.스텝();
        Expert expert = ExpertFixture.진로_전문가();

        ScheduleRequest oceanRequest = new ScheduleRequest(expert.getId(), LocalDateTime.of(2025, 4, 6, 14, 0, 0),
                "BASIC");
        scheduleService.create(oceanRequest, ocean.getId());

        ScheduleRequest stepRequest = new ScheduleRequest(expert.getId(), LocalDateTime.of(2025, 4, 6, 14, 0, 0),
                "BASIC");
        assertThatThrownBy(() -> scheduleService.create(stepRequest, step.getId())).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    void 사용자_일정_조회() {
        User ocean = UserFixture.오션();
        User step = UserFixture.스텝();
        Expert expert = ExpertFixture.진로_전문가();

        ScheduleRequest oceanRequest = new ScheduleRequest(expert.getId(), LocalDateTime.of(2025, 4, 6, 14, 0, 0),
                "BASIC");
        scheduleService.create(oceanRequest, ocean.getId());
    }
}

package com.mindgoal.domain.schedule.controller;

import com.mindgoal.domain.schedule.dto.ScheduleRequest;
import com.mindgoal.domain.schedule.dto.ScheduleResponse;
import com.mindgoal.domain.schedule.service.ScheduleService;
import com.mindgoal.domain.user.entity.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request,
                                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(scheduleService.create(request, principalDetails.getId()));
    }
}

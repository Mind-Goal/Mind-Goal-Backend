package com.mindgoal.domain.expert.controller;

import com.mindgoal.common.BaseResponse;
import com.mindgoal.common.BaseResponseStatus;
import com.mindgoal.domain.expert.dto.ExpertCreateRequest;
import com.mindgoal.domain.expert.dto.ExpertResponse;
import com.mindgoal.domain.expert.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expert")
public class ExpertController {
    private final ExpertService expertService;

    @PostMapping
    public ResponseEntity<BaseResponse<ExpertResponse>> register(
            @RequestBody @Valid ExpertCreateRequest request) {
        ExpertResponse response = expertService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(response, BaseResponseStatus.EXPERT_REGISTER_SUCCESS.getMessage()));
    }
}
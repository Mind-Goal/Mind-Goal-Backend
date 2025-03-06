package com.mindgoal.domain.matching.controller;

import com.mindgoal.common.BaseResponse;
import com.mindgoal.common.BaseResponseStatus;
import com.mindgoal.domain.matching.dto.MatchingRequest;
import com.mindgoal.domain.matching.dto.MatchingResponse;
import com.mindgoal.domain.matching.service.MatchingService;
import com.mindgoal.domain.user.entity.auth.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 매칭 API를 처리하는 컨트롤러 클래스
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/matching")
public class MatchingController {

    private final MatchingService matchingService;

    /**
     * 매칭 요청 API
     *
     * @param requestDto 매칭 요청 정보
     * @param principalDetails 인증된 사용자 정보
     * @return 생성된 매칭 정보
     */
    @PostMapping
    public ResponseEntity<BaseResponse<MatchingResponse>> requestMatching(
            @RequestBody @Valid MatchingRequest requestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        MatchingResponse response = matchingService.requestMatching(principalDetails.getId(), requestDto);
        return ResponseEntity
                .status(BaseResponseStatus.MATCHING_REQUEST_SUCCESS.getCode())
                .body(BaseResponse.success(response, BaseResponseStatus.MATCHING_REQUEST_SUCCESS.getMessage()));
    }
}
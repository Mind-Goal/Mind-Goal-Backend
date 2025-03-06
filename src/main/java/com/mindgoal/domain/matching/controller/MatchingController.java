package com.mindgoal.domain.matching.controller;

import com.mindgoal.common.BaseResponse;
import com.mindgoal.common.BaseResponseStatus;
import com.mindgoal.domain.matching.dto.MatchingRequestDto;
import com.mindgoal.domain.matching.dto.MatchingResponseDto;
import com.mindgoal.domain.matching.service.MatchingService;
import com.mindgoal.domain.user.entity.auth.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BaseResponse<MatchingResponseDto>> requestMatching(
            @RequestBody @Valid MatchingRequestDto requestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        MatchingResponseDto response = matchingService.requestMatching(principalDetails.getId(), requestDto);
        return ResponseEntity
                .status(BaseResponseStatus.MATCHING_REQUEST_SUCCESS.getCode())
                .body(BaseResponse.success(response, BaseResponseStatus.MATCHING_REQUEST_SUCCESS.getMessage()));
    }

    /**
     *
     *
     * @param matchingId 매칭 ID
     * @param principalDetails 인증된 사용자 정보
     * @return 취소된 매칭 정보
     */
    @GetMapping("/{matchingId}")
    public ResponseEntity<BaseResponse<MatchingResponseDto>> cancelMatching(
            @PathVariable Long matchingId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        MatchingResponseDto response = matchingService.cancelMatching(matchingId, principalDetails.getId());
        return ResponseEntity.ok(
                BaseResponse.success(response, BaseResponseStatus.MATCHING_CANCEL_SUCCESS.getMessage()));
    }
}
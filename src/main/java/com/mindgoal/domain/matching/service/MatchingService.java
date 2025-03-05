package com.mindgoal.domain.matching.service;
import com.mindgoal.common.BaseResponseStatus;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.expert.repository.ExpertRepository;
import com.mindgoal.domain.matching.dto.MatchingRequestDto;
import com.mindgoal.domain.matching.dto.MatchingResponseDto;
import com.mindgoal.domain.matching.entity.Matching;
import com.mindgoal.domain.matching.entity.MatchingStatus;
import com.mindgoal.domain.matching.repository.MatchingRepository;
import com.mindgoal.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final ExpertRepository expertRepository;

    @Transactional
    public MatchingResponseDto requestMatching(Long userId, MatchingRequestDto requestDto) {
        // 전문가 존재 여부 확인
        Expert expert = expertRepository.findById(requestDto.getExpertId())
                .orElseThrow(() -> new CustomException(BaseResponseStatus.EXPERT_NOT_FOUND));

        Matching matching = Matching.builder()
                .userId(userId)
                .expertId(requestDto.getExpertId())
                .status(MatchingStatus.PENDING.name())
                .requestMessage(requestDto.getRequestMessage())
                .build();

        // 매칭 저장
        Matching savedMatching = matchingRepository.save(matching);

        return MatchingResponseDto.from(savedMatching);
    }

    /**
     * 매칭을 취소하는 메서드
     *
     * @param matchingId 매칭 ID
     * @param userId 사용자 ID
     * @return 취소된 매칭 정보
     */
    @Transactional
    public MatchingResponseDto cancelMatching(Long matchingId, Long userId) {
        // 매칭 존재 여부 확인
        Matching matching = matchingRepository.findById(matchingId)
                .orElseThrow(() -> new CustomException(BaseResponseStatus.MATCHING_NOT_FOUND));

        // 요청자 확인
        if (!matching.getUserId().equals(userId)) {
            throw new CustomException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
        }

        // 이미 취소된 매칭인지 확인
        if (matching.getStatus().equals(MatchingStatus.CANCELED.name())) {
            throw new CustomException(BaseResponseStatus.MATCHING_ALREADY_CANCELED);
        }

        // 매칭 상태 변경
        matching.updateStatus(MatchingStatus.CANCELED.name());

        return MatchingResponseDto.from(matching);
    }

    /**
     * 사용자의 매칭 목록을 조회하는 메서드
     *
     * @param userId 사용자 ID
     * @return 매칭 목록
     */
    @Transactional(readOnly = true)
    public List<MatchingResponseDto> getMyMatchings(Long userId) {
        List<Matching> matchings = matchingRepository.findByUserId(userId);

        return matchings.stream()
                .map(MatchingResponseDto::from)
                .collect(Collectors.toList());
    }
}
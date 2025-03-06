package com.mindgoal.domain.matching.service;
import com.mindgoal.common.BaseResponseStatus;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.expert.repository.ExpertRepository;
import com.mindgoal.domain.matching.dto.MatchingRequest;
import com.mindgoal.domain.matching.dto.MatchingResponse;
import com.mindgoal.domain.matching.entity.Matching;
import com.mindgoal.domain.matching.entity.MatchingStatus;
import com.mindgoal.domain.matching.repository.MatchingRepository;
import com.mindgoal.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final ExpertRepository expertRepository;

    @Transactional
    public MatchingResponse requestMatching(Long userId, MatchingRequest requestDto) {
        // 전문가 존재 여부 확인
        Expert expert = findExpertById(requestDto.getExpertId());

        Matching matching = Matching.builder()
                .userId(userId)
                .expertId(requestDto.getExpertId())
                .status(MatchingStatus.PENDING.name())
                .requestMessage(requestDto.getRequestMessage())
                .build();

        // 저장하고 바로 DTO로 변환하여 반환
        return MatchingResponse.from(matchingRepository.save(matching));
    }

    /**
     * 매칭을 취소하는 메서드
     *
     * @param matchingId 매칭 ID
     * @param userId 사용자 ID
     * @return 취소된 매칭 정보
     */
    @Transactional
    public MatchingResponse cancelMatching(Long matchingId, Long userId) {
        // 매칭 조회
        Matching matching = findMatchingById(matchingId);

        // 매칭 검증
        validateMatching(matching, userId);

        // 매칭 상태 변경
        matching.updateStatus(MatchingStatus.CANCELED.name());

        return MatchingResponse.from(matching);
    }

    /**
     * 사용자의 매칭 목록을 조회하는 메서드
     *
     * @param userId 사용자 ID
     * @return 매칭 목록
     */
    @Transactional(readOnly = true)
    public List<MatchingResponse> getMyMatchings(Long userId) {
        List<Matching> matchings = matchingRepository.findAllByUserId(userId);

        return matchings.stream()
                .map(MatchingResponse::from)
                .toList();
    }

    /**
     * 전문가 ID로 전문가를 찾는 메서드
     *
     * @param expertId 전문가 ID
     * @return 전문가 엔티티
     * @throws CustomException 전문가를 찾을 수 없는 경우 예외 발생
     */
    private Expert findExpertById(Long expertId) {
        return expertRepository.findById(expertId)
                .orElseThrow(() -> new CustomException(BaseResponseStatus.EXPERT_NOT_FOUND));
    }

    private Matching findMatchingById(Long matchingId) {
        return matchingRepository.findById(matchingId)
                .orElseThrow(() -> new CustomException(BaseResponseStatus.MATCHING_NOT_FOUND));
    }

    private void validateMatching(Matching matching, Long userId) {
        validateMatchingOwner(matching, userId);
        validateMatchingStatus(matching);
    }

    private void validateMatchingOwner(Matching matching, Long userId) {
        if (!matching.getUserId().equals(userId)) {
            throw new CustomException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
        }
    }

    private void validateMatchingStatus(Matching matching) {
        if (matching.getStatus().equals(MatchingStatus.CANCELED.name())) {
            throw new CustomException(BaseResponseStatus.MATCHING_ALREADY_CANCELED);
        }
    }
}
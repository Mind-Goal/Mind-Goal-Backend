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
        // 매칭 조회
        Matching matching = findMatchingById(matchingId);

        // 매칭 검증
        validateMatching(matching, userId);

        // 매칭 상태 변경
        matching.updateStatus(MatchingStatus.CANCELED.name());

        return MatchingResponseDto.from(matching);
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
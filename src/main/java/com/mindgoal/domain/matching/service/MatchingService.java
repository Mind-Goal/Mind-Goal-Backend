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
}
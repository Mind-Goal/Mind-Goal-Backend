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
}
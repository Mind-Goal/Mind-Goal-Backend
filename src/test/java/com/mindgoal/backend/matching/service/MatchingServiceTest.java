package com.mindgoal.backend.matching.service;

import com.mindgoal.backend.support.annotation.ServiceTest;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.expert.repository.ExpertRepository;
import com.mindgoal.domain.matching.dto.MatchingRequestDto;
import com.mindgoal.domain.matching.dto.MatchingResponseDto;
import com.mindgoal.domain.matching.entity.Matching;
import com.mindgoal.domain.matching.entity.MatchingStatus;
import com.mindgoal.domain.matching.repository.MatchingRepository;
import com.mindgoal.domain.matching.service.MatchingService;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.repository.UserRepository;
import com.mindgoal.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ServiceTest
class MatchingServiceTest {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private MatchingRepository matchingRepository;

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        matchingRepository.deleteAll();
        expertRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("매칭 요청 성공")
    @Test
    void requestMatching_Success() {
        // given
        User user = createUser("user@example.com", "사용자");
        User expertUser = createUser("expert@example.com", "전문가");
        Expert expert = createExpert(expertUser.getId());

        MatchingRequestDto request = createMatchingRequest(expert.getId());

        // when
        MatchingResponseDto response = matchingService.requestMatching(user.getId(), request);

        // then
        assertThat(response)
                .satisfies(matchingResponse -> {
                    assertThat(matchingResponse.getUserId()).isEqualTo(user.getId());
                    assertThat(matchingResponse.getExpertId()).isEqualTo(expert.getId());
                    assertThat(matchingResponse.getStatus()).isEqualTo(MatchingStatus.PENDING.name());
                    assertThat(matchingResponse.getRequestMessage()).isEqualTo("축구 기술 향상을 위한 코칭 요청드립니다.");
                });

        // DB 검증
        Matching foundMatching = matchingRepository.findById(response.getId())
                .orElseThrow(() -> new AssertionError("Matching should exist"));

        assertThat(foundMatching)
                .satisfies(matching -> {
                    assertThat(matching.getUserId()).isEqualTo(user.getId());
                    assertThat(matching.getExpertId()).isEqualTo(expert.getId());
                    assertThat(matching.getStatus()).isEqualTo(MatchingStatus.PENDING.name());
                    assertThat(matching.getRequestMessage()).isEqualTo("축구 기술 향상을 위한 코칭 요청드립니다.");
                });
    }

    @DisplayName("존재하지 않는 전문가에게 매칭 요청 시 실패")
    @Test
    void requestMatching_ExpertNotFound_ThrowsException() {
        // given
        User user = createUser("user@example.com", "사용자");
        Long nonExistentExpertId = 999L;

        MatchingRequestDto request = createMatchingRequest(nonExistentExpertId);

        // when & then
        assertThrows(CustomException.class,
                () -> matchingService.requestMatching(user.getId(), request));
    }

    @DisplayName("매칭 요청 시 기본 상태는 PENDING")
    @Test
    void requestMatching_DefaultStatusIsPending() {
        // given
        User user = createUser("user@example.com", "사용자");
        User expertUser = createUser("expert@example.com", "전문가");
        Expert expert = createExpert(expertUser.getId());

        MatchingRequestDto request = createMatchingRequest(expert.getId());

        // when
        MatchingResponseDto response = matchingService.requestMatching(user.getId(), request);

        // then
        assertThat(response.getStatus()).isEqualTo(MatchingStatus.PENDING.name());

        // DB 검증
        Matching foundMatching = matchingRepository.findById(response.getId())
                .orElseThrow(() -> new AssertionError("Matching should exist"));
        assertThat(foundMatching.getStatus()).isEqualTo(MatchingStatus.PENDING.name());
    }

    private User createUser(String email, String name) {
        User user = User.builder()
                .email(email)
                .name(name)
                .isAgreePolicy(true)
                .build();
        return userRepository.save(user);
    }

    private Expert createExpert(Long userId) {
        Expert expert = Expert.builder()
                .userId(userId)
                .category("TECHNICAL")
                .speciality("축구 기술 트레이닝")
                .position("공격수")
                .region("서울")
                .careerYears(5)
                .description("전문가 설명")
                .careerHistory("경력 사항")
                .teachingMethod("교육 방식")
                .pricePerHour(50000)
                .isActive(true)
                .rating(0.0)
                .matchCount(0)
                .physicalScore(0)
                .techScore(0)
                .mentalScore(0)
                .build();
        return expertRepository.save(expert);
    }

    private MatchingRequestDto createMatchingRequest(Long expertId) {
        return MatchingRequestDto.builder()
                .expertId(expertId)
                .requestMessage("축구 기술 향상을 위한 코칭 요청드립니다.")
                .build();
    }
}
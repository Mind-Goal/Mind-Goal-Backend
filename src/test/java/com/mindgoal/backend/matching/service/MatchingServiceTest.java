package com.mindgoal.backend.matching.service;

import com.mindgoal.backend.support.annotation.ServiceTest;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.expert.repository.ExpertRepository;
import com.mindgoal.domain.matching.dto.MatchingRequest;
import com.mindgoal.domain.matching.dto.MatchingResponse;
import com.mindgoal.domain.matching.entity.Matching;
import com.mindgoal.domain.matching.entity.MatchingStatus;
import com.mindgoal.domain.matching.repository.MatchingRepository;
import com.mindgoal.domain.matching.service.MatchingService;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.repository.UserRepository;
import com.mindgoal.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @DisplayName("매칭 요청 성공")
    @Test
    void requestMatching_Success() {
        // given
        User user = createUser("user@example.com", "사용자");
        User expertUser = createUser("expert@example.com", "전문가");
        Expert expert = createExpert(expertUser.getId());

        MatchingRequest request = createMatchingRequest(expert.getId());

        // when
        MatchingResponse response = matchingService.requestMatching(user.getId(), request);

        // then
        assertThat(response)
                .satisfies(matchingResponse -> {
                    assertThat(matchingResponse.getUserId()).isEqualTo(user.getId());
                    assertThat(matchingResponse.getExpertId()).isEqualTo(expert.getId());
                    assertThat(matchingResponse.getStatus()).isEqualTo(MatchingStatus.PENDING.name());
                    assertThat(matchingResponse.getRequestMessage()).isEqualTo("축구 기술 향상을 위한 코칭 요청드립니다.");
                });
    }

    @DisplayName("매칭 취소 성공")
    @Test
    void cancelMatching_Success() {
        // given
        User user = createUser("user@example.com", "사용자");
        User expertUser = createUser("expert@example.com", "전문가");
        Expert expert = createExpert(expertUser.getId());

        // 매칭 생성
        Matching matching = createMatching(user.getId(), expert.getId());

        // when
        MatchingResponse response = matchingService.cancelMatching(matching.getId(), user.getId());

        // then
        assertThat(response.getStatus()).isEqualTo(MatchingStatus.CANCELED.name());

        // DB 검증
        Matching foundMatching = matchingRepository.findById(matching.getId())
                .orElseThrow(() -> new AssertionError("Matching should exist"));
        assertThat(foundMatching.getStatus()).isEqualTo(MatchingStatus.CANCELED.name());
    }

    @DisplayName("존재하지 않는 매칭 취소 시 실패")
    @Test
    void cancelMatching_NotFound_ThrowsException() {
        // given
        User user = createUser("user@example.com", "사용자");
        Long nonExistentMatchingId = 999L;

        // when & then
        assertThrows(CustomException.class,
                () -> matchingService.cancelMatching(nonExistentMatchingId, user.getId()));
    }

    @DisplayName("타인의 매칭 취소 시 실패")
    @Test
    void cancelMatching_UnauthorizedAccess_ThrowsException() {
        // given
        User user = createUser("user@example.com", "사용자");
        User otherUser = createUser("other@example.com", "다른사용자");
        User expertUser = createUser("expert@example.com", "전문가");
        Expert expert = createExpert(expertUser.getId());

        // user의 매칭 생성
        Matching matching = createMatching(user.getId(), expert.getId());

        // when & then
        assertThrows(CustomException.class,
                () -> matchingService.cancelMatching(matching.getId(), otherUser.getId()));
    }

    @DisplayName("이미 취소된 매칭 취소 시 실패")
    @Test
    void cancelMatching_AlreadyCanceled_ThrowsException() {
        // given
        User user = createUser("user@example.com", "사용자");
        User expertUser = createUser("expert@example.com", "전문가");
        Expert expert = createExpert(expertUser.getId());

        // 취소된 매칭 생성
        Matching matching = createCanceledMatching(user.getId(), expert.getId());

        // when & then
        assertThrows(CustomException.class,
                () -> matchingService.cancelMatching(matching.getId(), user.getId()));
    }

    @DisplayName("내 매칭 목록 조회 성공")
    @Test
    void getMyMatchings_Success() {
        // given
        User user = createUser("user@example.com", "사용자");
        User expertUser = createUser("expert@example.com", "전문가");
        Expert expert = createExpert(expertUser.getId());

        // 사용자의 매칭 3개 생성
        Matching matching1 = createMatchingWithMessage(user.getId(), expert.getId(), "첫 번째 요청");
        Matching matching2 = createMatchingWithMessage(user.getId(), expert.getId(), "두 번째 요청");
        Matching matching3 = createMatchingWithMessage(user.getId(), expert.getId(), "세 번째 요청");

        // when
        List<MatchingResponse> responses = matchingService.getMyMatchings(user.getId());

        // then
        assertThat(responses).hasSize(3);

        // 매칭 정보 확인
        assertThat(responses)
                .extracting(MatchingResponse::getUserId)
                .containsOnly(user.getId());

        assertThat(responses)
                .extracting(MatchingResponse::getExpertId)
                .containsOnly(expert.getId());

        assertThat(responses)
                .extracting(MatchingResponse::getRequestMessage)
                .containsExactlyInAnyOrder("첫 번째 요청", "두 번째 요청", "세 번째 요청");
    }

    @DisplayName("매칭이 없는 경우 빈 목록 반환")
    @Test
    void getMyMatchings_EmptyList() {
        // given
        User user = createUser("user@example.com", "사용자");

        // when
        List<MatchingResponse> responses = matchingService.getMyMatchings(user.getId());

        // then
        assertThat(responses).isEmpty();
    }

    @DisplayName("다른 사용자의 매칭은 조회되지 않음")
    @Test
    void getMyMatchings_OtherUserMatchingsNotIncluded() {
        // given
        User user1 = createUser("user1@example.com", "사용자1");
        User user2 = createUser("user2@example.com", "사용자2");
        User expertUser = createUser("expert@example.com", "전문가");
        Expert expert = createExpert(expertUser.getId());

        // 사용자1의 매칭 2개 생성
        Matching matching1 = createMatchingWithMessage(user1.getId(), expert.getId(), "사용자1의 요청1");
        Matching matching2 = createMatchingWithMessage(user1.getId(), expert.getId(), "사용자1의 요청2");

        // 사용자2의 매칭 1개 생성
        Matching matching3 = createMatchingWithMessage(user2.getId(), expert.getId(), "사용자2의 요청");

        // when
        List<MatchingResponse> user1Responses = matchingService.getMyMatchings(user1.getId());
        List<MatchingResponse> user2Responses = matchingService.getMyMatchings(user2.getId());

        // then
        assertThat(user1Responses).hasSize(2);
        assertThat(user2Responses).hasSize(1);

        // 사용자1의 매칭만 조회되는지 확인
        assertThat(user1Responses)
                .extracting(MatchingResponse::getRequestMessage)
                .containsExactlyInAnyOrder("사용자1의 요청1", "사용자1의 요청2");

        // 사용자2의 매칭만 조회되는지 확인
        assertThat(user2Responses)
                .extracting(MatchingResponse::getRequestMessage)
                .containsExactly("사용자2의 요청");
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

    private MatchingRequest createMatchingRequest(Long expertId) {
        return MatchingRequest.builder()
                .expertId(expertId)
                .requestMessage("축구 기술 향상을 위한 코칭 요청드립니다.")
                .build();
    }

    private Matching createMatching(Long userId, Long expertId) {
        Matching matching = Matching.builder()
                .userId(userId)
                .expertId(expertId)
                .status(MatchingStatus.PENDING.name())
                .requestMessage("축구 기술 향상을 위한 코칭 요청드립니다.")
                .build();
        return matchingRepository.save(matching);
    }

    private Matching createMatchingWithMessage(Long userId, Long expertId, String requestMessage) {
        Matching matching = Matching.builder()
                .userId(userId)
                .expertId(expertId)
                .status(MatchingStatus.PENDING.name())
                .requestMessage(requestMessage)
                .build();
        return matchingRepository.save(matching);
    }

    private Matching createCanceledMatching(Long userId, Long expertId) {
        Matching matching = Matching.builder()
                .userId(userId)
                .expertId(expertId)
                .status(MatchingStatus.CANCELED.name())
                .requestMessage("취소된 요청입니다.")
                .build();
        return matchingRepository.save(matching);
    }
}
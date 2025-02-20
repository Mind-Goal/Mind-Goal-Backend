package com.mindgoal.backend.expert.service;

import com.mindgoal.backend.support.annotation.ServiceTest;
import com.mindgoal.domain.expert.dto.*;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.expert.repository.ExpertRepository;
import com.mindgoal.domain.expert.service.ExpertService;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.repository.UserRepository;
import com.mindgoal.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ServiceTest
class ExpertServiceTest {

    @Autowired
    private ExpertService expertService;

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        expertRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("전문가 등록 성공")
    @Test
    void register_Success() {
        // given
        User user = createUser("test@example.com", "testUser");
        ExpertCreateRequest request = createExpertRequest();

        // when
        ExpertResponse response = expertService.register(request, user.getId());

        // then
        assertThat(response)
                .satisfies(expertResponse -> {
                    assertThat(expertResponse.getCategory()).isEqualTo("TECHNICAL");
                    assertThat(expertResponse.getSpeciality()).isEqualTo("축구 기술 트레이닝");
                    assertThat(expertResponse.getPosition()).isEqualTo("공격수");
                    assertThat(expertResponse.getRegion()).isEqualTo("서울");
                    assertThat(expertResponse.getRating()).isEqualTo(0.0);
                    assertThat(expertResponse.getMatchCount()).isEqualTo(0);
                    assertThat(expertResponse.isActive()).isTrue();
                    assertThat(expertResponse.getPhysicalScore()).isEqualTo(0);
                    assertThat(expertResponse.getTechScore()).isEqualTo(0);
                    assertThat(expertResponse.getMentalScore()).isEqualTo(0);
                });

        // DB 검증
        Expert foundExpert = expertRepository.findById(response.getId())
                .orElseThrow(() -> new AssertionError("Expert should exist"));
        assertThat(foundExpert)
                .satisfies(expert -> {
                    assertThat(expert.getCategory()).isEqualTo("TECHNICAL");
                    assertThat(expert.getSpeciality()).isEqualTo("축구 기술 트레이닝");
                    assertThat(expert.getPosition()).isEqualTo("공격수");
                    assertThat(expert.getRegion()).isEqualTo("서울");
                    assertThat(expert.getCareerYears()).isEqualTo(5);
                });
    }

    @DisplayName("이미 등록된 전문가인 경우 실패")
    @Test
    void register_AlreadyExistExpert_ThrowsException() {
        // given
        User user = createUser("test@example.com", "testUser");
        Expert expert = createExpert(user.getId());
        ExpertCreateRequest request = createExpertRequest();

        // when & then
        assertThrows(CustomException.class,
                () -> expertService.register(request, user.getId()));
    }

    @DisplayName("전문가 등록시 초기값 설정 확인")
    @Test
    void register_CheckInitialValues() {
        // given
        User user = createUser("test@example.com", "testUser");
        ExpertCreateRequest request = createExpertRequest();

        // when
        ExpertResponse response = expertService.register(request, user.getId());

        // then
        assertThat(response)
                .satisfies(expertResponse -> {
                    assertThat(expertResponse.getRating()).isEqualTo(0.0);
                    assertThat(expertResponse.getMatchCount()).isEqualTo(0);
                    assertThat(expertResponse.isActive()).isTrue();
                    assertThat(expertResponse.getPhysicalScore()).isEqualTo(0);
                    assertThat(expertResponse.getTechScore()).isEqualTo(0);
                    assertThat(expertResponse.getMentalScore()).isEqualTo(0);
                });
    }

    @DisplayName("전문가 상세 조회 성공")
    @Test
    void getExpertDetail_Success() {
        // given
        User user = createUser("test@example.com", "testUser");
        Expert expert = createExpert(user.getId());

        // when
        ExpertDetailResponse response = expertService.getExpertDetail(expert.getId());

        // then
        assertThat(response.getId()).isEqualTo(expert.getId());
        assertThat(response.getUserId()).isEqualTo(user.getId());
        assertThat(response.getName()).isEqualTo(user.getName());
        assertThat(response.getCategory()).isEqualTo("TECHNICAL");
        assertThat(response.getSpeciality()).isEqualTo("축구 기술 트레이닝");
        assertThat(response.getPosition()).isEqualTo("공격수");
        assertThat(response.getRegion()).isEqualTo("서울");
        assertThat(response.getCareerYears()).isEqualTo(5);
        assertThat(response.getDescription()).isEqualTo("전문가 설명");
        assertThat(response.getCareerHistory()).isEqualTo("경력 사항");
        assertThat(response.getTeachingMethod()).isEqualTo("교육 방식");
        assertThat(response.getPricePerHour()).isEqualTo(50000);

        // Scores
        assertThat(response.getScores())
                .extracting("physical", "tech", "mental")
                .containsExactly(0, 0, 0);

        // Links
        assertThat(response.getLinks())
                .extracting("youtube", "instagram")
                .containsOnlyNulls();

        // Status
        assertThat(response.isActive()).isTrue();
        assertThat(response.getRating()).isZero();
        assertThat(response.getMatchCount()).isZero();

        // Timestamps
        assertThat(response.getCreatedAt()).isNotNull();
        assertThat(response.getUpdatedAt()).isNotNull();
    }

    @DisplayName("존재하지 않는 전문가 조회시 실패")
    @Test
    void getExpertDetail_NotFound_ThrowsException() {
        // given
        Long nonExistentExpertId = 999L;

        // when & then
        assertThrows(CustomException.class,
                () -> expertService.getExpertDetail(nonExistentExpertId));
    }

    @DisplayName("전문가 상세 조회시 연관된 사용자 정보 확인")
    @Test
    void getExpertDetail_CheckUserInfo() {
        // given
        User user = createUser("test@example.com", "testUser");
        Expert expert = createExpert(user.getId());

        // when
        ExpertDetailResponse response = expertService.getExpertDetail(expert.getId());

        // then
        assertThat(response)
                .satisfies(expertResponse -> {
                    assertThat(expertResponse.getUserId()).isEqualTo(user.getId());
                    assertThat(expertResponse.getName()).isEqualTo(user.getName());
                });
    }

    @DisplayName("전문가 정보 수정 성공")
    @Test
    void updateExpert_Success() {
        // given
        User user = createUser("test@example.com", "testUser");
        Expert expert = createExpert(user.getId());

        ExpertUpdateRequest request = ExpertUpdateRequest.builder()
                .specialty("수정된 전문 분야")
                .position("수정된 포지션")
                .description("수정된 설명")
                .careerHistory("수정된 경력")
                .teachingMethod("수정된 교육방식")
                .pricePerHour(60000)
                .youtubeUrl("https://youtube.com/updated")
                .instagramUrl("https://instagram.com/updated")
                .build();

        // when
        ExpertUpdateResponse response = expertService.updateExpert(expert.getId(), request, user.getId());

        // then
        Expert updatedExpert = expertRepository.findById(expert.getId())
                .orElseThrow(() -> new AssertionError("Expert should exist"));

        assertThat(updatedExpert)
                .satisfies(e -> {
                    assertThat(e.getSpeciality()).isEqualTo("수정된 전문 분야");
                    assertThat(e.getPosition()).isEqualTo("수정된 포지션");
                    assertThat(e.getDescription()).isEqualTo("수정된 설명");
                    assertThat(e.getCareerHistory()).isEqualTo("수정된 경력");
                    assertThat(e.getTeachingMethod()).isEqualTo("수정된 교육방식");
                    assertThat(e.getPricePerHour()).isEqualTo(60000);
                    assertThat(e.getYoutubeUrl()).isEqualTo("https://youtube.com/updated");
                    assertThat(e.getInstagramUrl()).isEqualTo("https://instagram.com/updated");
                });

        assertThat(response)
                .satisfies(r -> {
                    assertThat(r.getId()).isEqualTo(expert.getId());
                    assertThat(r.getUpdatedAt()).isNotNull();
                });
    }

    @DisplayName("전문가 정보 부분 수정 성공")
    @Test
    void updateExpert_PartialUpdate_Success() {
        // given
        User user = createUser("test@example.com", "testUser");
        Expert expert = createExpert(user.getId());

        ExpertUpdateRequest request = ExpertUpdateRequest.builder()
                .specialty("수정된 전문 분야")
                .pricePerHour(60000)
                .build();

        // when
        ExpertUpdateResponse response = expertService.updateExpert(expert.getId(), request, user.getId());

        // then
        Expert updatedExpert = expertRepository.findById(expert.getId())
                .orElseThrow(() -> new AssertionError("Expert should exist"));

        assertThat(updatedExpert)
                .satisfies(e -> {
                    // 수정된 필드
                    assertThat(e.getSpeciality()).isEqualTo("수정된 전문 분야");
                    assertThat(e.getPricePerHour()).isEqualTo(60000);

                    // 기존 값이 유지되어야 하는 필드들
                    assertThat(e.getPosition()).isEqualTo("공격수");
                    assertThat(e.getDescription()).isEqualTo("전문가 설명");
                    assertThat(e.getCareerHistory()).isEqualTo("경력 사항");
                    assertThat(e.getTeachingMethod()).isEqualTo("교육 방식");
                });

        assertThat(response.getId()).isEqualTo(expert.getId());
    }

    @DisplayName("존재하지 않는 전문가 정보 수정 실패")
    @Test
    void updateExpert_NotFound_ThrowsException() {
        // given
        User user = createUser("test@example.com", "testUser");
        Long nonExistentExpertId = 999L;

        ExpertUpdateRequest request = ExpertUpdateRequest.builder()
                .specialty("수정된 전문 분야")
                .build();

        // when & then
        assertThrows(CustomException.class,
                () -> expertService.updateExpert(nonExistentExpertId, request, user.getId()));
    }

    @DisplayName("권한 없는 사용자의 전문가 정보 수정 실패")
    @Test
    void updateExpert_UnauthorizedAccess_ThrowsException() {
        // given
        User owner = createUser("owner@example.com", "owner");
        User other = createUser("other@example.com", "other");
        Expert expert = createExpert(owner.getId());

        ExpertUpdateRequest request = ExpertUpdateRequest.builder()
                .specialty("수정된 전문 분야")
                .build();

        // when & then
        assertThrows(CustomException.class,
                () -> expertService.updateExpert(expert.getId(), request, other.getId()));
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

    private ExpertCreateRequest createExpertRequest() {
        return ExpertCreateRequest.builder()
                .category("TECHNICAL")
                .speciality("축구 기술 트레이닝")
                .position("공격수")
                .region("서울")
                .careerYears(5)
                .description("10년 이상의 축구 선수 경력")
                .careerHistory("프로팀 경력 5년")
                .teachingMethod("1:1 맞춤형 기술 훈련")
                .pricePerHour(50000)
                .build();
    }
}
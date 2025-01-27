package com.mindgoal.backend.expert.service;

import com.mindgoal.backend.support.annotation.ServiceTest;
import com.mindgoal.domain.expert.dto.ExpertCreateRequest;
import com.mindgoal.domain.expert.dto.ExpertDetailResponse;
import com.mindgoal.domain.expert.dto.ExpertResponse;
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

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
        expertRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("전문가 등록 성공")
    @Test
    void register_Success() {
        // given
        User user = createUser("test@example.com", "testUser");
        setSecurityContext(user.getEmail());
        ExpertCreateRequest request = createExpertRequest();

        // when
        ExpertResponse response = expertService.register(request);

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
        setSecurityContext(user.getEmail());
        Expert expert = createExpert(user.getId());
        ExpertCreateRequest request = createExpertRequest();

        // when & then
        assertThrows(CustomException.class,
                () -> expertService.register(request));
    }

    @DisplayName("전문가 등록시 초기값 설정 확인")
    @Test
    void register_CheckInitialValues() {
        // given
        User user = createUser("test@example.com", "testUser");
        setSecurityContext(user.getEmail());
        ExpertCreateRequest request = createExpertRequest();

        // when
        ExpertResponse response = expertService.register(request);

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

    private void setSecurityContext(String email) {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        SecurityContextHolder.setContext(securityContext);
    }

    private User createUser(String email, String name) {
        User user = User.builder()
                .email(email)
                .password("password1234")
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
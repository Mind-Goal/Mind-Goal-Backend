package com.mindgoal.domain.expert.service;

import com.mindgoal.domain.expert.dto.ExpertCreateRequest;
import com.mindgoal.domain.expert.dto.ExpertResponse;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.expert.repository.ExpertRepository;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExpertService {
    private final ExpertRepository expertRepository;
    private final UserService userService;
        @Transactional
        public ExpertResponse register(ExpertCreateRequest request) {
            // 현재 로그인한 사용자 정보 가져오기
            User currentUser = userService.getCurrentUser();

            // 이미 전문가로 등록된 사용자인지 확인
            if (expertRepository.existsByUserId(currentUser.getId())) {
                throw new IllegalArgumentException("이미 등록된 전문가입니다.");
            }

            Expert expert = Expert.builder()
                    .userId(currentUser.getId())
                    .category(request.getCategory())
                    .speciality(request.getSpeciality())
                    .position(request.getPosition())
                    .careerYears(request.getCareerYears())
                    .description(request.getDescription())
                    .careerHistory(request.getCareerHistory())
                    .teachingMethod(request.getTeachingMethod())
                    .pricePerHour(request.getPricePerHour())
                    .youtubeUrl(request.getYoutubeUrl())
                    .instagramUrl(request.getInstagramUrl())
                    .isActive(true)
                    .build();

            Expert savedExpert = expertRepository.save(expert);
            return ExpertResponse.from(savedExpert);
        }

    public Expert getExpert(Long id) {
        return expertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("전문가를 찾을 수 없습니다."));
    }
}
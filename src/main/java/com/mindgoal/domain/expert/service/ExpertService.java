package com.mindgoal.domain.expert.service;

import com.mindgoal.domain.expert.dto.ExpertCreateRequest;
import com.mindgoal.domain.expert.dto.ExpertListResponse;
import com.mindgoal.domain.expert.dto.ExpertResponse;
import com.mindgoal.domain.expert.dto.ExpertSearchCondition;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.expert.repository.ExpertRepository;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        User currentUser = userService.getCurrentUser();

        if (expertRepository.existsByUserId(currentUser.getId())) {
            throw new IllegalArgumentException("이미 등록된 전문가입니다.");
        }

        Expert expert = Expert.builder()
                .userId(currentUser.getId())
                .category(request.getCategory())
                .speciality(request.getSpeciality())
                .position(request.getPosition())
                .region(request.getRegion())
                .careerYears(request.getCareerYears())
                .description(request.getDescription())
                .careerHistory(request.getCareerHistory())
                .teachingMethod(request.getTeachingMethod())
                .pricePerHour(request.getPricePerHour())
                .youtubeUrl(request.getYoutubeUrl())
                .instagramUrl(request.getInstagramUrl())
                .isActive(true)
                .rating(0.0)
                .matchCount(0)
                .physicalScore(0)
                .techScore(0)
                .mentalScore(0)
                .build();

        Expert savedExpert = expertRepository.save(expert);
        return ExpertResponse.from(savedExpert);
    }

    public Page<ExpertListResponse> searchExperts(ExpertSearchCondition condition, Pageable pageable) {
        Page<Expert> experts = expertRepository.findAllByFilters(condition, pageable);

        return experts.map(expert -> {
            User user = userService.getUser(expert.getUserId());
            return ExpertListResponse.builder()
                    .id(expert.getId())
                    .name(user.getName())
                    .category(expert.getCategory())
                    .position(expert.getPosition())
                    .specialty(expert.getSpeciality())
                    .region(expert.getRegion())
                    .careerYears(expert.getCareerYears())
                    .rating(expert.getRating())
                    .matchCount(expert.getMatchCount())
                    .technical(expert.getTechScore())
                    .physical(expert.getPhysicalScore())
                    .mental(expert.getMentalScore())
                    .build();
        });
    }
}
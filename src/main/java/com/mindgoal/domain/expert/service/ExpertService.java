package com.mindgoal.domain.expert.service;

import com.mindgoal.common.BaseResponseStatus;
import com.mindgoal.domain.expert.dto.*;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.expert.repository.ExpertRepository;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.service.UserService;
import com.mindgoal.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpertService {
    private final ExpertRepository expertRepository;
    private final UserService userService;

    @Transactional
    public ExpertResponse register(ExpertCreateRequest request) {
        User currentUser = userService.getCurrentUser();

        if (expertRepository.existsByUserId(currentUser.getId())) {
            throw new CustomException(BaseResponseStatus.EXPERT_ALREADY_EXISTS);
        }

        Expert expert = createExpertEntity(currentUser, request);
        Expert savedExpert = expertRepository.save(expert);
        return ExpertResponse.from(savedExpert);
    }

    @Transactional(readOnly = true)
    public Expert getExpert(Long id) {
        return expertRepository.findExpertById(id);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public ExpertDetailResponse getExpertDetail(Long expertId) {
        Expert expert = expertRepository.findExpertById(expertId);
        User user = userService.getUser(expert.getUserId());
        return ExpertDetailResponse.of(expert, user.getName());
    }

    @Transactional
    public ExpertUpdateResponse updateExpert(Long expertId, ExpertUpdateRequest request) {
        Expert expert = expertRepository.findExpertById(expertId);
        User currentUser = userService.getCurrentUser();

        if (!expert.getUserId().equals(currentUser.getId())) {
            throw new CustomException(BaseResponseStatus.UNAUTHORIZED_ACCESS);
        }

        expert.update(
                request.getSpecialty(),
                request.getPosition(),
                request.getDescription(),
                request.getCareerHistory(),
                request.getTeachingMethod(),
                request.getPricePerHour(),
                request.getYoutubeUrl(),
                request.getInstagramUrl()
        );

        return ExpertUpdateResponse.from(expert);
    }

    private Expert createExpertEntity(User user, ExpertCreateRequest request) {
        return Expert.builder()
                .userId(user.getId())
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
    }


}

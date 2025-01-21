package com.mindgoal.domain.expert.service;

import com.mindgoal.common.BaseResponseStatus;
import com.mindgoal.domain.expert.dto.ExpertCreateRequest;
import com.mindgoal.domain.expert.dto.ExpertResponse;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.expert.repository.ExpertRepository;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.service.UserService;
import com.mindgoal.exception.CustomException;
import lombok.RequiredArgsConstructor;
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

    public Expert getExpert(Long id) {
        return expertRepository.findById(id)
                .orElseThrow(() -> new CustomException(BaseResponseStatus.EXPERT_NOT_FOUND));
    }

    private Expert createExpertEntity(User user, ExpertCreateRequest request) {
        return Expert.builder()
                .userId(user.getId())
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
    }
}
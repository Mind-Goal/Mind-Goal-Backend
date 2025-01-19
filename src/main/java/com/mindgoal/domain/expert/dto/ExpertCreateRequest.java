package com.mindgoal.domain.expert.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpertCreateRequest {
    @NotBlank(message = "카테고리는 필수입니다")
    private String category;

    @NotBlank(message = "전문 분야는 필수입니다")
    private String speciality;

    @NotBlank(message = "포지션은 필수입니다")
    private String position;

    @NotBlank(message = "활동 지역은 필수입니다")
    private String region;

    @Positive(message = "경력 연수는 양수여야 합니다")
    private int careerYears;

    @NotBlank(message = "설명은 필수입니다")
    private String description;

    private String careerHistory;

    private String teachingMethod;

    @Positive(message = "시간당 비용은 양수여야 합니다")
    private int pricePerHour;

    private String youtubeUrl;
    private String instagramUrl;

    @Builder
    public ExpertCreateRequest(String category, String speciality,
                               String position, String region, int careerYears,
                               String description, String careerHistory,
                               String teachingMethod, int pricePerHour,
                               String youtubeUrl, String instagramUrl) {
        this.category = category;
        this.speciality = speciality;
        this.position = position;
        this.region = region;
        this.careerYears = careerYears;
        this.description = description;
        this.careerHistory = careerHistory;
        this.teachingMethod = teachingMethod;
        this.pricePerHour = pricePerHour;
        this.youtubeUrl = youtubeUrl;
        this.instagramUrl = instagramUrl;
    }
}
package com.mindgoal.domain.expert.dto;

import com.mindgoal.domain.expert.entity.Expert;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class ExpertDetailResponse {
    private Long id;
    private Long userId;
    private String name;
    private String position;
    private String category;
    private String region;
    private String speciality;
    private String description;
    private String careerHistory;
    private String teachingMethod;
    private int careerYears;
    private int matchCount;
    private double rating;
    private boolean isActive;
    private int pricePerHour;
    private Scores scores;
    private Links links;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @Builder
    public static class Scores {
        private int physical;
        private int tech;
        private int mental;
    }

    @Getter
    @Builder
    public static class Links {
        private String youtube;
        private String instagram;
    }

    public static ExpertDetailResponse from(Expert expert, String userName) {
        return ExpertDetailResponse.builder()
                .id(expert.getId())
                .userId(expert.getUserId())
                .name(userName)
                .position(expert.getPosition())
                .category(expert.getCategory())
                .region(expert.getRegion())
                .speciality(expert.getSpeciality())
                .description(expert.getDescription())
                .careerHistory(expert.getCareerHistory())
                .teachingMethod(expert.getTeachingMethod())
                .careerYears(expert.getCareerYears())
                .matchCount(expert.getMatchCount())
                .rating(expert.getRating())
                .isActive(expert.isActive())
                .pricePerHour(expert.getPricePerHour())
                .scores(Scores.builder()
                        .physical(expert.getPhysicalScore())
                        .tech(expert.getTechScore())
                        .mental(expert.getMentalScore())
                        .build())
                .links(Links.builder()
                        .youtube(expert.getYoutubeUrl())
                        .instagram(expert.getInstagramUrl())
                        .build())
                .createdAt(expert.getCreatedAt())
                .updatedAt(expert.getUpdatedAt())
                .build();
    }
}
package com.mindgoal.domain.expert.dto;

import com.mindgoal.domain.expert.entity.Expert;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpertResponse {
    private final Long id;
    private final String category;
    private final String speciality;
    private final String position;
    private final double rating;
    private final int matchCount;
    private final boolean isActive;
    private final int physicalScore;
    private final int techScore;
    private final int mentalScore;

    @Builder
    private ExpertResponse(Long id, String category, String speciality,
                           String position, double rating, int matchCount, boolean isActive,
                           int physicalScore, int techScore, int mentalScore) {
        this.id = id;
        this.category = category;
        this.speciality = speciality;
        this.position = position;
        this.rating = rating;
        this.matchCount = matchCount;
        this.isActive = isActive;
        this.physicalScore = physicalScore;
        this.techScore = techScore;
        this.mentalScore = mentalScore;
    }

    public static ExpertResponse from(Expert expert) {
        return ExpertResponse.builder()
                .id(expert.getId())
                .category(expert.getCategory())
                .speciality(expert.getSpeciality())
                .position(expert.getPosition())
                .rating(expert.getRating())
                .matchCount(expert.getMatchCount())
                .isActive(expert.isActive())
                .physicalScore(expert.getPhysicalScore())
                .techScore(expert.getTechScore())
                .mentalScore(expert.getMentalScore())
                .build();
    }


}
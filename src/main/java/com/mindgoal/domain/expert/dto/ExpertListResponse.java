package com.mindgoal.domain.expert.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpertListResponse {
    private final Long id;
    private final String name;
    private final String category;
    private final String position;
    private final String specialty;
    private final String region;
    private final int careerYears;
    private final double rating;
    private final int matchCount;
    private final ExpertScores scores;

    @Builder
    public ExpertListResponse(Long id, String name, String category,
                              String position, String specialty, String region,
                              int careerYears, double rating, int matchCount,
                              int technical, int physical, int mental) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.position = position;
        this.specialty = specialty;
        this.region = region;
        this.careerYears = careerYears;
        this.rating = rating;
        this.matchCount = matchCount;
        this.scores = new ExpertScores(technical, physical, mental);
    }

    @Getter
    public static class ExpertScores {
        private final int technical;
        private final int physical;
        private final int mental;

        public ExpertScores(int technical, int physical, int mental) {
            this.technical = technical;
            this.physical = physical;
            this.mental = mental;
        }
    }
}
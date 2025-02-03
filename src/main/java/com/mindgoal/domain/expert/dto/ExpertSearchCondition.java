package com.mindgoal.domain.expert.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpertSearchCondition {
    private String category;
    private String position;
    private String region;
    private Integer minCareerYears;
    private Integer maxCareerYears;
    private Double minRating;
    private Integer minPrice;
    private Integer maxPrice;

    public static ExpertSearchCondition ofDetail(
            String category, String position, String region,
            Integer minCareerYears, Integer maxCareerYears,
            Double minRating, Integer minPrice, Integer maxPrice) {
        return ExpertSearchCondition.builder()
                .category(category)
                .position(position)
                .region(region)
                .minCareerYears(minCareerYears)
                .maxCareerYears(maxCareerYears)
                .minRating(minRating)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
    }
}
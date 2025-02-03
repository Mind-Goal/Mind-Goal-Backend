package com.mindgoal.domain.expert.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Builder
public class ExpertSearchRequest {
    private final String category;
    private final String position;
    private final String region;
    private final Integer minCareerYears;
    private final Integer maxCareerYears;
    private final Double minRating;
    private final Integer minPrice;
    private final Integer maxPrice;

    @Builder.Default
    private final int page = 0;

    @Builder.Default
    private final int size = 20;

    @Builder.Default
    private final String sortBy = "rating";

    @Builder.Default
    private final String direction = "desc";

    public Pageable toPageable() {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        return PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
    }

    public ExpertSearchCondition toCondition() {
        return ExpertSearchCondition.ofDetail(
                category, position, region,
                minCareerYears, maxCareerYears,
                minRating, minPrice, maxPrice
        );
    }
}
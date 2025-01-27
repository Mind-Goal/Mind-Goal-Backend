package com.mindgoal.domain.expert.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpertUpdateRequest {
    private String specialty;
    private String position;
    private String description;
    private String careerHistory;
    private String teachingMethod;
    private Integer pricePerHour;
    private String youtubeUrl;
    private String instagramUrl;
}
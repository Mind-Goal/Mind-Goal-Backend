package com.mindgoal.domain.expert.dto;

import com.mindgoal.domain.expert.entity.Expert;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ExpertUpdateResponse {
    private Long id;
    private LocalDateTime updatedAt;

    public static ExpertUpdateResponse from(Expert expert) {
        return ExpertUpdateResponse.builder()
                .id(expert.getId())
                .updatedAt(expert.getUpdatedAt())
                .build();
    }
}
package com.mindgoal.domain.matching.dto;

import com.mindgoal.domain.matching.entity.Matching;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingResponseDto {
    private Long id;
    private Long userId;
    private Long expertId;
    private String status;
    private String requestMessage;
    private LocalDate matchedAt;

    public static MatchingResponseDto from(Matching matching) {
        return MatchingResponseDto.builder()
                .id(matching.getId())
                .userId(matching.getUserId())
                .expertId(matching.getExpertId())
                .status(matching.getStatus())
                .requestMessage(matching.getRequestMessage())
                .matchedAt(matching.getMatchedAt())
                .build();
    }
}
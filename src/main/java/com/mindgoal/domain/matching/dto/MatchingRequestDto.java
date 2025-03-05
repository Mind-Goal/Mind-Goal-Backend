package com.mindgoal.domain.matching.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingRequestDto {

    @NotNull(message = "전문가 ID는 필수 입력값입니다")
    private Long expertId;

    @NotBlank(message = "요청 메시지는 필수 입력값입니다")
    private String requestMessage;
}
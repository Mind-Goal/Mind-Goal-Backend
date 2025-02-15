package com.mindgoal.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private String key;
}

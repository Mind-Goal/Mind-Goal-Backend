package com.mindgoal.config.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiresIn;
}

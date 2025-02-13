package com.mindgoal.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OauthRefresh {

    private String token_type;
    private String access_token;
    private Integer expires_in;

    private String grant_type;
    private String client_id;
    private String refresh_token;
}

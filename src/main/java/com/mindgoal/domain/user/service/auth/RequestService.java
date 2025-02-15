package com.mindgoal.domain.user.service.auth;

import com.mindgoal.domain.user.dto.JwtResponse;
import com.mindgoal.domain.user.dto.KakaoUnlinkResponse;
import com.mindgoal.domain.user.dto.TokenResponse;

public interface RequestService<T> {
    JwtResponse redirect(String provider, String code, String state);

    TokenResponse getToken(String code);

    T getUserInfo(String accessToken);

    KakaoUnlinkResponse unLink(String accessToken);
}

package com.mindgoal.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfo {
    private Long id;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Getter
    @NoArgsConstructor
    public static class Properties {
        private String nickname;
        private String profile_image;
    }

    @Getter
    @NoArgsConstructor
    public static class KakaoAccount {
        private String email;
    }
}
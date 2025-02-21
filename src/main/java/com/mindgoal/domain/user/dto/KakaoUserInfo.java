package com.mindgoal.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfo {

    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    public String getEmail() {
        return kakaoAccount != null ? kakaoAccount.getEmail() : null;
    }

    public String getName() {
        return kakaoAccount != null && kakaoAccount.getProfile() != null
                ? kakaoAccount.getProfile().getNickname() : null;
    }

    @Getter
    public static class KakaoAccount {
        private String email;

        @JsonProperty("profile")
        private Profile profile;

        @Getter
        public static class Profile {
            @JsonProperty("nickname")
            private String nickname;

            @JsonProperty("profile_image")
            private String profile_image;
        }
    }
}

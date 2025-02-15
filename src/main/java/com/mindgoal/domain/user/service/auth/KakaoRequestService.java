package com.mindgoal.domain.user.service.auth;


import com.mindgoal.config.jwt.JwtTokenProvider;
import com.mindgoal.domain.refreshToken.entity.RefreshToken;
import com.mindgoal.domain.refreshToken.repository.RefreshTokenRepository;
import com.mindgoal.domain.user.dto.JwtResponse;
import com.mindgoal.domain.user.dto.KakaoUnlinkResponse;
import com.mindgoal.domain.user.dto.KakaoUserInfo;
import com.mindgoal.domain.user.dto.OauthRefresh;
import com.mindgoal.domain.user.dto.TokenDto;
import com.mindgoal.domain.user.dto.TokenResponse;
import com.mindgoal.domain.user.dto.UserResponse;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.repository.UserRepository;
import com.mindgoal.domain.user.service.UserService;
import com.mindgoal.domain.user.service.feign.KakaoAuthClient;
import com.mindgoal.domain.user.service.feign.KakaoInfoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoRequestService implements RequestService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoInfoClient kakaoInfoClient;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String GRANT_TYPE;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${spring.security.oauth2.client.provider.kakao.token_uri}")
    private String TOKEN_URI;

    @Override
    public JwtResponse redirect(final String provider, final String code, final String state) {
        // 카카오에서 넘겨준 엑세스 토큰
        TokenResponse tokenResponse = getToken(code);
        System.out.println(tokenResponse);
        // 카카오에서 넘겨준 유저 정보
        KakaoUserInfo kakaoUserInfo = getUserInfo(tokenResponse.getAccessToken());

        User user = userRepository.findByEmail(kakaoUserInfo.getEmail()).orElse(null);

        // 회원 가입이 되어있는 경우
        TokenDto newToken_AccessToken = jwtTokenProvider.createAccessToken(kakaoUserInfo.getEmail());
        TokenDto newToken_RefreshToken = jwtTokenProvider.createRefreshToken(kakaoUserInfo.getEmail());

        // 회원 가입이 안되어있는 경우(최초 로그인 시)
        if (user == null) {
            user = UserResponse.toEntity(userService.signUp(kakaoUserInfo.getEmail(), kakaoUserInfo.getName()));

            RefreshToken newRefreshToken = RefreshToken.toEntity(user.getEmail(),
                    newToken_RefreshToken.getRefreshToken());

            refreshTokenRepository.save(newRefreshToken);

            return getBuild(newToken_AccessToken.getAccessToken(), newToken_RefreshToken.getRefreshToken(), user,
                    tokenResponse.getRefreshToken());
        }

        RefreshToken refreshToken = refreshTokenRepository.findByKeyEmail(user.getEmail())
                .orElseThrow(IllegalArgumentException::new);

        System.out.println(refreshToken);
        return getBuild(newToken_AccessToken.getAccessToken(), refreshToken.getRefreshToken(), user,
                tokenResponse.getRefreshToken());
    }

    private JwtResponse getBuild(final String accessToken, final String refreshToken, final User user,
                                 final String oauthRefreshToken) {
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .oauthRefreshToken(oauthRefreshToken)
                .build();
    }

    @Override
    public TokenResponse getToken(final String code) {
        return kakaoAuthClient.getToken(GRANT_TYPE, CLIENT_ID, REDIRECT_URI, code);
    }

    @Override
    public KakaoUserInfo getUserInfo(final String accessToken) {
        return kakaoInfoClient.getUserInfo("Bearer " + accessToken);
    }

    @Override
    public KakaoUnlinkResponse unLink(final String accessToken) {
        return kakaoInfoClient.unlink("Bearer " + accessToken);
    }

    public OauthRefresh refresh(final String refreshToken) {
        return kakaoAuthClient.refresh("refresh_token", CLIENT_ID, refreshToken);
    }

}

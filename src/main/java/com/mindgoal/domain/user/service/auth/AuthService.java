package com.mindgoal.domain.user.service.auth;


import com.mindgoal.config.jwt.JwtTokenProvider;
import com.mindgoal.domain.refreshToken.entity.RefreshToken;
import com.mindgoal.domain.refreshToken.repository.RefreshTokenRepository;
import com.mindgoal.domain.refreshToken.service.RefreshTokenService;
import com.mindgoal.domain.user.dto.JwtResponse;
import com.mindgoal.domain.user.dto.KakaoUnlinkResponse;
import com.mindgoal.domain.user.dto.OauthRefresh;
import com.mindgoal.domain.user.dto.TokenDto;
import com.mindgoal.domain.user.dto.UserDeleteResponse;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoRequestService kakaoRequestService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public JwtResponse redirect(final String provider, final String code, final String state) {
        return kakaoRequestService.redirect(provider, code, state);
    }

    @Transactional
    public JwtResponse refreshToken(final String refreshToken) {

        if (jwtTokenProvider.isExpired(refreshToken)) {
            // refresh token 만료시 재로그인 필요
            throw new IllegalArgumentException();
        }

        RefreshToken refreshTokenObj = refreshTokenService.findByRefreshToken(refreshToken);

        User user = userRepository.findUserByEmail(refreshTokenObj.getKeyEmail());

        TokenDto newToken = jwtTokenProvider.createAccessToken(user.getEmail());

        return buildSignInResponse(newToken.getAccessToken(), refreshToken, user.getId());
    }

    private JwtResponse buildSignInResponse(final String accessToken, final String refreshToken, final Long userId) {
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userId)
                .build();
    }

    @Transactional
    public UserDeleteResponse deleteUser(final Long userId, final String oauthRefreshToken) {

        User currentUser = userRepository.findUserById(userId);

        deleteAll(currentUser);

        OauthRefresh dto = kakaoRequestService.refresh(oauthRefreshToken);

        KakaoUnlinkResponse unlink = kakaoRequestService.unLink(dto.getAccess_token());

        return UserDeleteResponse.from(unlink);
    }

    // 연관키로 묶여 있음
    private void deleteAll(final User currentUser) {
        refreshTokenRepository.deleteByKeyEmail(currentUser.getEmail());
        userRepository.delete(currentUser);
    }

}


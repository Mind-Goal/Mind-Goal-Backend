package com.mindgoal.domain.refreshToken.service;


import com.mindgoal.domain.refreshToken.entity.RefreshToken;
import com.mindgoal.domain.refreshToken.repository.RefreshTokenRepository;
import com.mindgoal.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public RefreshToken save(final String refreshToken, final String email, final Long expirationMs) {
        userRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);

        // 기존의 만료된 리프레시 토큰 삭제
        if (refreshTokenRepository.existsByKeyEmail(email)) {
            refreshTokenRepository.deleteByKeyEmail(email);
        }

        RefreshToken rt = RefreshToken.builder()
                .keyEmail(email)
                .refreshToken(refreshToken)
                .expiration(LocalDateTime.now().plusSeconds(expirationMs / 1000))
                .build();

        return refreshTokenRepository.save(rt);
    }

    @Transactional(readOnly = true)
    public RefreshToken findByRefreshToken(final String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(IllegalArgumentException::new);

    }
}

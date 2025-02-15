package com.mindgoal.domain.refreshToken.repository;

import com.mindgoal.domain.refreshToken.entity.RefreshToken;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    boolean existsByKeyEmail(String email);

    void deleteByKeyEmail(String email);

    Optional<RefreshToken> findByKeyEmail(String email);
}

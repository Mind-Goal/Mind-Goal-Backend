package com.mindgoal.domain.refreshToken.entity;

import com.mindgoal.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@Table(name = "REFRESH_TOKEN_TB")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id", nullable = false)
    private Long refreshTokenId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "key_email")
    private String keyEmail;

    private LocalDateTime expiration;

    public static RefreshToken toEntity(String email, String refreshToken) {
        return RefreshToken.builder()
                .keyEmail(email)
                .refreshToken(refreshToken)
                .build();
    }
}

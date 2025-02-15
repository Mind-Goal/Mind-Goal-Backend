package com.mindgoal.config.jwt;

import static com.mindgoal.common.BaseResponseStatus.EXPIRED_ACCESS_TOKEN;
import static com.mindgoal.common.BaseResponseStatus.UNSUPPORTED;
import static com.mindgoal.common.BaseResponseStatus.TOKEN_NOT_FOUND;

import com.mindgoal.domain.user.dto.TokenDto;
import com.mindgoal.domain.user.entity.auth.PrincipalDetails;
import com.mindgoal.domain.user.service.auth.PrincipalDetailService;
import com.mindgoal.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${custom.jwt.secret-key}")
    private String SECRET_KEY;
    private static final Long ACCESS_TOKEN_EXPIRE_COUNT = 3 * 60 * 60 * 1000L;
    private static final Long REFRESH_TOKEN_EXPIRE_COUNT = 7 * 24 * 60 * 60 * 1000L;

    private final PrincipalDetailService principalDetailService;

    public TokenDto createAccessToken(String email) {
        Claims claims = Jwts.claims().setSubject(email); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_COUNT)) // 토큰 만료 시간
                // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return TokenDto.builder()
                .accessToken(accessToken)
                .key(email)
                .build();
    }

    public TokenDto createRefreshToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_COUNT))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return TokenDto.builder()
                .refreshToken(refreshToken)
                .key(email)
                .build();
    }

    public boolean isExpired(String token) {
        return new Date(Long.parseLong(decodeToken(token).get("exp")) * 1000).before(new Date());
    }

    public Authentication getAuthentication(String token) {

        PrincipalDetails principalDetails = principalDetailService.loadUserByUsername(getPayload(token));
        return new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);

            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new CustomException(EXPIRED_ACCESS_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(UNSUPPORTED);
        } catch (MalformedJwtException | IllegalArgumentException e) {
            throw new CustomException(TOKEN_NOT_FOUND);
        }
    }

    public String getPayload(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    private Map<String, String> decodeToken(String token) {
        String[] split = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(split[1]));
        payload = payload.replaceAll("[{}\"]", "");

        Map<String, String> map = new HashMap<>();

        String[] contents = payload.split(",");
        for (String content : contents) {
            String[] c = content.split(":");
            map.put(c[0], c[1]);
        }

        return map;
    }
}

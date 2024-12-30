package com.mindgoal.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenProvider {

    private final Key key;
    private final long VALID_MILISECOND = 1000L * 60 * 60; // 1시간

    public JwtTokenProvider(@Value("${}") String secretkey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private String getUserName(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    public boolean valideToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                                    .setSigningKey(key)
                                    .build()
                                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken){
        //유저 데이터 가져와서 비교
        return null;
    }


    public TokenDto generateToken(String userName){
        long now = (new Date()).getTime();
        Claims claims = Jwts.claims().setSubject(userName);
        Date accessTokenExpiresIn = new Date(now + VALID_MILISECOND);
        String accessToken = Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
                .grantType("Bearer ")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .build();
    }
}

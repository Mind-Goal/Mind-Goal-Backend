package com.mindgoal.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenProvider {

    @Value("${}") String secretkey;
    private final long VALID_MILISECOND = 1000L * 60 * 60; // 1시간

    private Key getSecretKey(@Value("${}") String secretkey){
        byte[] keyBytes = secretkey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String getUserName(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(secretkey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    public boolean valideToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                                    .setSigningKey(getSecretKey(secretkey))
                                    .build()
                                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken){
        //유저 데이터 가져와서 비교
        return
    }


    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + VALID_MILISECOND))
                .signWith(getSecretKey(secretkey), SignatureAlgorithm.HS256)
                .compact();
    }
}

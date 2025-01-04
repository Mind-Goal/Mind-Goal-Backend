package com.mindgoal.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String AUTHORAIZATION_Header = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String jwtToken = parseJwt(request);

            if(jwtToken != null && jwtTokenProvider.valideToken(jwtToken)){
                Authentication auth = jwtTokenProvider.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (ExpiredJwtException e){
            response.sendError(HttpStatus.UNAUTHORIZED.value(),"토큰이 만료되었습니다.");
        }catch (JwtException e){
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "유효한 토큰이 아닙니다.");
        }catch (Exception e){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"서버의 오류가 발생되었습니다.");
        }

        filterChain.doFilter(request, response);

    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader(AUTHORAIZATION_Header);
        if(StringUtils.hasText(headerAuth)&& headerAuth.startsWith(BEARER_PREFIX)){
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}

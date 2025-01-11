package com.mindgoal.domain.user.service;

import com.fasterxml.jackson.databind.JsonNode;

import com.mindgoal.domain.user.dto.KakaoUserInfo;
import com.mindgoal.domain.user.dto.TokenDto;
import com.mindgoal.domain.user.dto.UpdateUserRequest;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.repository.UserRepository;
import com.mindgoal.config.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${KAKAO_CLIENT_ID}")
    private String clientId;

    @Value("${KAKAO_REDIRECT_URI}")
    private String redirectUri;

    @Transactional
    public void logout(HttpServletRequest request) {
        // SecurityContext 초기화
        SecurityContextHolder.clearContext();

        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 현재 스레드의 로컬 컨텍스트 정리
        SecurityContextHolder.getContext().setAuthentication(null);
    }
    @Transactional
    public TokenDto kakaoLogin(String code) {
        String accessToken = getKakaoAccessToken(code);
        KakaoUserInfo userInfo = getKakaoUserInfo(accessToken);

        User user = userRepository.findByEmailAndIsDeletedFalse(userInfo.getKakao_account().getEmail())
                .orElseGet(() -> createKakaoUser(userInfo));
        return new TokenDto(jwtTokenProvider.generateToken(user.getEmail()));
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    private String getKakaoAccessToken(String code) {
        WebClient.ResponseSpec response = WebClient.create("https://kauth.kakao.com")
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("code", code)
                        .build())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve();

        JsonNode jsonNode = response.bodyToMono(JsonNode.class).block();
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfo getKakaoUserInfo(String accessToken) {
        return WebClient.create("https://kapi.kakao.com")
                .get()
                .uri("/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfo.class)
                .block();
    }

    @Transactional
    public User createKakaoUser(KakaoUserInfo userInfo) {
        String email = userInfo.getKakao_account().getEmail();

        return userRepository.findByEmailAndIsDeletedFalse(email)
                .map(existingUser -> {
                    // 기존 사용자의 kakaoId 업데이트 로직 필요시 추가
                    return existingUser;
                })
                .orElseGet(() -> {
                    return userRepository.save(User.builder()
                            .email(email)
                            .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                            .name(userInfo.getProperties().getNickname())
                            .profileImage(userInfo.getProperties().getProfile_image())
                            .isAgreePolicy(true)
                            .build());
                });
    }

    @Transactional
    public User updateUser(UpdateUserRequest request) {
        User user = getCurrentUser();
        user.updateProfile(request.getName(), request.getProfileImage());
        return userRepository.save(user);
    }

    @Transactional
    public void withdrawUser() {
        User user = getCurrentUser();
        userRepository.delete(user);
        SecurityContextHolder.clearContext();
    }

}
package com.mindgoal.domain.user.controller;

import com.mindgoal.domain.user.dto.KakaoLoginRequest;
import com.mindgoal.domain.user.dto.TokenDto;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/oauth/login")
    public ResponseEntity<TokenDto> kakaoLogin(@RequestBody KakaoLoginRequest request) {
        TokenDto token = userService.kakaoLogin(request.getCode());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/user/me")
    public ResponseEntity<User> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }
}
package com.mindgoal.domain.user.controller;

import com.mindgoal.common.BaseResponse;
import com.mindgoal.domain.user.dto.KakaoLoginRequest;
import com.mindgoal.domain.user.dto.TokenDto;
import com.mindgoal.domain.user.dto.UpdateUserRequest;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/oauth/login")
    public ResponseEntity<BaseResponse<TokenDto>> kakaoLogin(@RequestBody KakaoLoginRequest request) {
        TokenDto token = userService.kakaoLogin(request.getCode());
        return ResponseEntity.ok(BaseResponse.success(token, "카카오 로그인 성공"));
    }

    @PostMapping("/oauth/logout")
    public ResponseEntity<BaseResponse<Void>> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok(BaseResponse.success(null, "로그아웃 성공"));
    }

    @GetMapping("/user/me")
    public ResponseEntity<BaseResponse<User>> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(BaseResponse.success(user, "사용자 정보 조회 성공"));
    }

    @PutMapping("/user/me")
    public ResponseEntity<BaseResponse<User>> updateMyInfo(@RequestBody @Valid UpdateUserRequest request) {
        User updatedUser = userService.updateUser(request);
        return ResponseEntity.ok(BaseResponse.success(updatedUser, "내 정보 수정 성공"));
    }
}
package com.mindgoal.domain.user.controller;

import com.mindgoal.common.BaseResponse;
import com.mindgoal.domain.user.dto.JwtResponse;
import com.mindgoal.domain.user.dto.KakaoLoginRequest;
import com.mindgoal.domain.user.dto.TokenDto;
import com.mindgoal.domain.user.dto.UpdateUserRequest;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.service.UserService;
import com.mindgoal.domain.user.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

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

    @DeleteMapping("/user/me")
    public ResponseEntity<BaseResponse<Void>> withdrawUser() {
        userService.withdrawUser();
        return ResponseEntity.ok(BaseResponse.success(null, "회원 탈퇴 성공"));
    }
}

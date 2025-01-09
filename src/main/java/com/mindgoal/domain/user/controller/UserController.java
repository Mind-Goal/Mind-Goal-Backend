package com.mindgoal.domain.user.controller;


import com.mindgoal.domain.user.dto.ApiResponse;
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
    public ResponseEntity<TokenDto> kakaoLogin(@RequestBody KakaoLoginRequest request) {
        TokenDto token = userService.kakaoLogin(request.getCode());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/oauth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/me")
    public ResponseEntity<User> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }
    @PutMapping("/user/me")
    public ResponseEntity<ApiResponse<User>> updateMyInfo(@RequestBody @Valid UpdateUserRequest request) {
        User updatedUser = userService.updateUser(request);
        return ResponseEntity.ok(ApiResponse.success(updatedUser, "내 정보 수정 성공"));
    }

}
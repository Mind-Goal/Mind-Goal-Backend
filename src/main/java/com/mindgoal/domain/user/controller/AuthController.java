package com.mindgoal.domain.user.controller;

import com.mindgoal.domain.user.dto.JwtResponse;
import com.mindgoal.domain.user.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login/oauth2/code/{registrationId}")
    public ResponseEntity<JwtResponse> redirect(
            @PathVariable("registrationId") final String registrationId,
            @RequestParam("code") final String code,
            @RequestParam("state") final String state
    ) {

        JwtResponse jwt = authService.redirect(registrationId, code, state);

        return ResponseEntity.ok(jwt);
    }
}

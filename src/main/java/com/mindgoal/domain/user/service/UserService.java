package com.mindgoal.domain.user.service;


import com.mindgoal.domain.user.dto.UpdateUserRequest;
import com.mindgoal.domain.user.dto.UserResponse;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.repository.UserRepository;
import com.mindgoal.config.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponse signUp(final String email, final String name) {
        User user = User.builder()
                .email(email)
                .name(name)
                .build();

        return UserResponse.from(userRepository.save(user));
    }

    @Transactional
    public void logout(HttpServletRequest request) {
        // SecurityContext 초기화
        SecurityContextHolder.clearContext();

        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @Transactional
    public User updateUser(UpdateUserRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.updateProfile(request.getName(), request.getProfileImage());
        return userRepository.save(user);
    }

    @Transactional
    public void withdrawUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.withdraw();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
package com.mindgoal.backend.user;

import com.mindgoal.domain.user.dto.UpdateUserRequest;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.repository.UserRepository;
import com.mindgoal.domain.user.service.UserService;
import com.mindgoal.backend.support.annotation.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ServiceTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("사용자 정보 업데이트 성공")
    @Test
    void updateUser_Success() {
        // given
        User savedUser = createUser("test@example.com", "oldName", "old.jpg");

        UpdateUserRequest request = UpdateUserRequest.builder()
                .name("newName")
                .profileImage("newImage.jpg")
                .build();

        // when
        User updatedUser = userService.updateUser(request);

        // then
        assertThat(updatedUser)
                .extracting("name", "profileImage")
                .containsExactly("newName", "newImage.jpg");

        // DB 검증
        User foundUser = userRepository.findByEmailAndIsDeletedFalse(savedUser.getEmail())
                .orElseThrow(() -> new AssertionError("User should exist"));
        assertThat(foundUser)
                .extracting("name", "profileImage")
                .containsExactly("newName", "newImage.jpg");
    }

    @DisplayName("존재하지 않는 사용자 업데이트 실패")
    @Test
    void updateUser_UserNotFound_ThrowsException() {
        // given
        UpdateUserRequest request = UpdateUserRequest.builder()
                .name("newName")
                .profileImage("newImage.jpg")
                .build();

        // when & then
        assertThrows(UsernameNotFoundException.class,
                () -> userService.updateUser(request));
    }

    @DisplayName("사용자 정보 부분 업데이트 - 이름만 변경")
    @Test
    void updateUser_NameOnly() {
        // given
        User savedUser = createUser("test@example.com", "oldName", "old.jpg");

        UpdateUserRequest request = UpdateUserRequest.builder()
                .name("newName")
                .build();

        // when
        User updatedUser = userService.updateUser(request);

        // then
        assertThat(updatedUser)
                .extracting("name", "profileImage")
                .containsExactly("newName", "old.jpg");
    }

    private User createUser(String email, String name, String profileImage) {
        User user = User.builder()
                .email(email)
                .password("password1234")
                .name(name)
                .profileImage(profileImage)
                .build();
        return userRepository.save(user);
    }
}
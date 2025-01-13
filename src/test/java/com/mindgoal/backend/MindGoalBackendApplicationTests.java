package com.mindgoal.backend;

import com.mindgoal.domain.user.dto.UpdateUserRequest;
import com.mindgoal.domain.user.entity.User;
import com.mindgoal.domain.user.repository.UserRepository;
import com.mindgoal.domain.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SecurityContext securityContext;

	@Mock
	private Authentication authentication;

	@BeforeEach
	void setUp() {
		SecurityContextHolder.clearContext();
	}

	@AfterEach
	void tearDown() {
		SecurityContextHolder.clearContext();
	}

	@DisplayName("사용자 정보 업데이트 성공")
	@Test
	void updateUser_Success() {
		// given
		String email = "test@example.com";
		String newName = "newName";
		String newProfileImage = "newImage.jpg";

		User existingUser = User.builder()
				.email(email)
				.name("oldName")
				.profileImage("old.jpg")
				.build();

		UpdateUserRequest request = UpdateUserRequest.builder()
				.name(newName)
				.profileImage(newProfileImage)
				.build();

		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getName()).thenReturn(email);
		SecurityContextHolder.setContext(securityContext);

		when(userRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(any(User.class))).thenReturn(existingUser);

		// when
		User updatedUser = userService.updateUser(request);

		// then
		assertThat(updatedUser.getName()).isEqualTo(newName);
		assertThat(updatedUser.getProfileImage()).isEqualTo(newProfileImage);
		verify(userRepository).findByEmailAndIsDeletedFalse(email);
		verify(userRepository).save(any(User.class));
	}

	@DisplayName("존재하지 않는 사용자 업데이트 실패")
	@Test
	void updateUser_UserNotFound_ThrowsException() {
		// given
		String email = "nonexistent@example.com";
		UpdateUserRequest request = UpdateUserRequest.builder()
				.name("newName")
				.profileImage("newImage.jpg")
				.build();

		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getName()).thenReturn(email);
		SecurityContextHolder.setContext(securityContext);

		when(userRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(Optional.empty());

		// when & then
		assertThrows(UsernameNotFoundException.class,
				() -> userService.updateUser(request));
		verify(userRepository).findByEmailAndIsDeletedFalse(email);
		verify(userRepository, never()).save(any(User.class));
	}
}
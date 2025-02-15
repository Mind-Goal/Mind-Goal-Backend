package com.mindgoal.domain.user.dto;

import com.mindgoal.domain.user.entity.User;
import jakarta.persistence.Column;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String profileImage;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .profileImage(user.getProfileImage())
                .build();
    }

    public static List<UserResponse> from(List<User> users) {
        return users.stream()
                .map(UserResponse::from)
                .toList();
    }

    public static User toEntity(UserResponse response) {
        return User.builder()
                .id(response.getId())
                .email(response.getEmail())
                .name(response.getName())
                .phoneNumber(response.getPhoneNumber())
                .profileImage(response.getProfileImage())
                .build();
    }
}

package com.mindgoal.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateUserRequest {
    private String name;
    private String profileImage;

    @Builder
    public UpdateUserRequest(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }
}
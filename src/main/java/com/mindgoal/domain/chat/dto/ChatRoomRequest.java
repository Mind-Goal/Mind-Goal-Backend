package com.mindgoal.domain.chat.dto;

import lombok.Getter;

@Getter
public class ChatRoomRequest {
    private final Long expertId;
    private final String profile_image;

    public ChatRoomRequest(Long id, String profile_image) {
        this.expertId = id;
        this.profile_image = profile_image;
    }
}

package com.mindgoal.domain.chat.dto;

import lombok.Getter;

@Getter
public class ChatMessageRequest {
    private Long chatRoomId;
    private Long senderId;
    private String content;
}

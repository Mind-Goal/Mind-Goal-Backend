package com.mindgoal.domain.chat.dto;

import com.mindgoal.domain.chat.entity.ChatMessage;

public record ChatMessageResponse(Long id, Long userId, Long chatRoomId, String content, int readCount) {

    public static ChatMessageResponse from(final ChatMessage chatMessage) {
        return new ChatMessageResponse(chatMessage.getId(), chatMessage.getUserId(), chatMessage.getChatRoomId(),
                chatMessage.getContent(), chatMessage.getReadCount());
    }
}

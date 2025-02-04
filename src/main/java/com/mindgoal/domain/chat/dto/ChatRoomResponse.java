package com.mindgoal.domain.chat.dto;

import com.mindgoal.domain.chat.entity.ChatRoom;
import java.time.LocalDate;

public record ChatRoomResponse(Long id, Long expertId, Long userId, Long headCount, LocalDate lastMessageAt,
                               Boolean isActive) {

    public static ChatRoomResponse from(final ChatRoom chatRoom) {
        return new ChatRoomResponse(chatRoom.getId(), chatRoom.getExpertId(), chatRoom.getUserId(),
                chatRoom.getHeadCount(), chatRoom.getLastMessageAt(), chatRoom.getIsActive());
    }
}

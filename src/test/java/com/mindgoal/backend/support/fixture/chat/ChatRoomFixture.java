package com.mindgoal.backend.support.fixture.chat;

import com.mindgoal.domain.chat.entity.ChatRoom;
import com.mindgoal.domain.chat.entity.ChatRoomStatus;
import java.time.LocalDate;

public class ChatRoomFixture {
    public static ChatRoom 오션_심리_채팅방() {
        return new ChatRoom(1L, 1L, new ChatRoomStatus(0L, LocalDate.now(), true));
    }
}

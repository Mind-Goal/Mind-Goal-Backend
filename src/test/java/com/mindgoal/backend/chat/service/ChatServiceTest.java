package com.mindgoal.backend.chat.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mindgoal.backend.support.annotation.ServiceTest;
import com.mindgoal.backend.support.fixture.chat.ChatRoomFixture;
import com.mindgoal.backend.support.fixture.expert.ExpertFixture;
import com.mindgoal.backend.support.fixture.user.UserFixture;
import com.mindgoal.domain.chat.dto.ChatRoomRequest;
import com.mindgoal.domain.chat.entity.ChatRoom;
import com.mindgoal.domain.chat.repository.ChatRoomRepository;
import com.mindgoal.domain.chat.service.ChatService;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
public class ChatServiceTest {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatService chatService;

    @Test
    void 채팅방_조회() {
        User user = UserFixture.오션();
        ChatRoom chatRoom1 = ChatRoomFixture.오션_심리_채팅방();
        ChatRoom chatRoom2 = ChatRoomFixture.오션_진로_채팅방();
        chatRoomRepository.save(chatRoom1);
        chatRoomRepository.save(chatRoom2);

        assertThat(chatService.getMyChatRooms(user.getId()).size()).isEqualTo(chatRoomRepository.count());
    }
}

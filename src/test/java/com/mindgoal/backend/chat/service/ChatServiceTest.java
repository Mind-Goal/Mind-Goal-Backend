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
    void 채팅방_생성() {
        Expert expert = ExpertFixture.심리_전문가();
        User user = UserFixture.오션();
        ChatRoomRequest chatRoomRequest = new ChatRoomRequest(expert.getId(), "defaultImage");
        chatService.saveChatRoom(chatRoomRequest, user.getId());
        assertThat(chatRoomRepository.count()).isNotNull();
    }

    @Test
    void 채팅방이_이미_존재할_경우_에외처리() {
        Expert expert = ExpertFixture.심리_전문가();
        User user = UserFixture.오션();
        ChatRoomRequest chatRoomRequest = new ChatRoomRequest(expert.getId(), "defaultImage");
        ChatRoom chatRoom = ChatRoomFixture.오션_심리_채팅방();
        chatRoomRepository.save(chatRoom);
        assertThatThrownBy(() -> chatService.saveChatRoom(chatRoomRequest, user.getId())).isExactlyInstanceOf(
                IllegalArgumentException.class);
    }

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

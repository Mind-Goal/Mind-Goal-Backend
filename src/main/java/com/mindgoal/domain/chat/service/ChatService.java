package com.mindgoal.domain.chat.service;

import com.mindgoal.domain.chat.dto.ChatRoomRequest;
import com.mindgoal.domain.chat.dto.ChatRoomResponse;
import com.mindgoal.domain.chat.entity.ChatRoom;
import com.mindgoal.domain.chat.entity.ChatRoomStatus;
import com.mindgoal.domain.chat.repository.ChatMessageRepository;
import com.mindgoal.domain.chat.repository.ChatRoomRepository;
import com.mindgoal.domain.user.entity.auth.PrincipalDetails;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatRoomResponse saveChatRoom(final ChatRoomRequest chatRoomRequest,
                                         final PrincipalDetails user) {
        final ChatRoom chatRoom = createChatRoom(chatRoomRequest, user);
        return ChatRoomResponse.from(chatRoom);
    }

    private void validateExisted(final Long expertId, final Long userId) {
        if (chatRoomRepository.findByExpertIdAndUserId(expertId, userId).isPresent()) {
            throw new IllegalArgumentException("already exist");
        }
    }

    private ChatRoom createChatRoom(final ChatRoomRequest chatRoomRequest, final PrincipalDetails user) {
        validateExisted(chatRoomRequest.getExpertId(), user.getId());
        final ChatRoomStatus chatRoomStatus = new ChatRoomStatus(0L, LocalDate.now(),true);
        return new ChatRoom(chatRoomRequest.getExpertId(), user.getId(), chatRoomStatus);
    }
}

package com.mindgoal.domain.chat.service;

import com.mindgoal.domain.chat.dto.ChatRoomRequest;
import com.mindgoal.domain.chat.dto.ChatRoomResponse;
import com.mindgoal.domain.chat.entity.ChatRoom;
import com.mindgoal.domain.chat.entity.ChatRoomStatus;
import com.mindgoal.domain.chat.repository.ChatMessageRepository;
import com.mindgoal.domain.chat.repository.ChatRoomRepository;
import com.mindgoal.domain.expert.repository.ExpertRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ExpertRepository expertRepository;

    @Transactional
    public ChatRoomResponse saveChatRoom(final ChatRoomRequest chatRoomRequest,
                                         final Long userId) {
        final ChatRoom chatRoom = createChatRoom(chatRoomRequest, userId);
        return ChatRoomResponse.from(chatRoom);
    }

    private void validateExisted(final Long expertId, final Long userId) {
        if (chatRoomRepository.existsByExpertIdAndUserId(expertId, userId)) {
            throw new IllegalArgumentException("already exist");
        }
    }

    private ChatRoom createChatRoom(final ChatRoomRequest chatRoomRequest, final Long userId) {
        validateExisted(chatRoomRequest.getExpertId(), userId);
        final ChatRoomStatus chatRoomStatus = ChatRoomStatus.createDefaultStatus();
        return new ChatRoom(chatRoomRequest.getExpertId(), userId, chatRoomStatus);
    }

    public List<ChatRoomResponse> getMyChatRooms(final Long userId) {
        if(isExpert(userId)){
            final Long expertId = expertRepository.findExpertByUserId(userId).getId();
            return chatRoomRepository.findAllByExpertId(expertId).stream()
                    .map(ChatRoomResponse::from)
                    .toList();
        }
        return chatRoomRepository.findAllByUserId(userId).stream()
                .map(ChatRoomResponse::from)
                .toList();
    }

    private boolean isExpert(final Long userId) {
        return expertRepository.existsByUserId(userId);
    }
}

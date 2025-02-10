package com.mindgoal.domain.chat.service;

import com.mindgoal.domain.chat.dto.ChatMessageRequest;
import com.mindgoal.domain.chat.dto.ChatMessageResponse;
import com.mindgoal.domain.chat.dto.ChatRoomRequest;
import com.mindgoal.domain.chat.dto.ChatRoomResponse;
import com.mindgoal.domain.chat.entity.ChatMessage;
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
    public ChatMessageResponse sendMessage(final Long userId, final ChatMessageRequest chatMessageRequest) {
        ChatRoom chatRoom = chatRoomRepository.findByExpertIdAndUserId(chatMessageRequest.expertId(), userId)
                .orElseGet(() -> {
                    ChatRoom newChatRoom = createChatRoom(chatMessageRequest, userId);
                    return chatRoomRepository.save(newChatRoom);
                });

        ChatMessage chatMessage = new ChatMessage(chatRoom.getId(), userId, chatMessageRequest.content());
        return ChatMessageResponse.from(chatMessageRepository.save(chatMessage));
    }

    private ChatRoom createChatRoom(final ChatMessageRequest chatMessageRequest, final Long userId) {
        final ChatRoomStatus chatRoomStatus = ChatRoomStatus.createDefaultStatus();
        return new ChatRoom(chatMessageRequest.expertId(), userId, chatRoomStatus);
    }

    public List<ChatRoomResponse> getMyChatRooms(final Long userId) {
        if (isExpert(userId)) {
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

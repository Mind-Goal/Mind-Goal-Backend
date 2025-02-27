package com.mindgoal.domain.chat.service;

import com.mindgoal.domain.chat.dto.ChatMessageRequest;
import com.mindgoal.domain.chat.dto.ChatMessageResponse;
import com.mindgoal.domain.chat.entity.ChatMessage;
import com.mindgoal.domain.chat.entity.ChatRoom;
import com.mindgoal.domain.chat.repository.ChatMessageRepository;
import com.mindgoal.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessageResponse sendMessage(final Long userId, final ChatMessageRequest chatMessageRequest) {
        ChatRoom chatRoom = chatRoomRepository.findByExpertIdAndUserId(chatMessageRequest.expertId(), userId);

        ChatMessage chatMessage = new ChatMessage(chatRoom.getId(), userId, chatMessageRequest.content());
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        return ChatMessageResponse.from(savedMessage);
    }
}

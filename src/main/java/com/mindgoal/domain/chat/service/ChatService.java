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
        System.out.println("sendMessage() called");
        System.out.println("User ID: " + userId);
        System.out.println("Expert ID: " + chatMessageRequest.expertId());
        System.out.println("Content: " + chatMessageRequest.content());

        ChatRoom chatRoom = chatRoomRepository.findByExpertIdAndUserId(chatMessageRequest.expertId(), userId);
        System.out.println("Chat Room ID: " + chatRoom.getId());

        ChatMessage chatMessage = new ChatMessage(chatRoom.getId(), userId, chatMessageRequest.content());
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        System.out.println("Message saved with ID: " + savedMessage.getId());

        return ChatMessageResponse.from(savedMessage);
    }
}

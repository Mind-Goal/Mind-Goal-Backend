package com.mindgoal.domain.chat.service;

import com.mindgoal.domain.chat.dto.ChatRoomRequest;
import com.mindgoal.domain.chat.dto.ChatRoomResponse;
import com.mindgoal.domain.chat.entity.ChatRoom;
import com.mindgoal.domain.chat.entity.ChatRoomStatus;
import com.mindgoal.domain.chat.repository.ChatRoomRepository;
import com.mindgoal.domain.expert.repository.ExpertRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ExpertRepository expertRepository;

    public ChatRoomResponse createChatRoom(final Long userId, final ChatRoomRequest chatRoomRequest) {
        final ChatRoomStatus chatRoomStatus = ChatRoomStatus.createDefaultStatus();
        final ChatRoom chatRoom = new ChatRoom(userId, chatRoomRequest.expertId(), chatRoomStatus);
        return ChatRoomResponse.from(chatRoomRepository.save(chatRoom));
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

package com.mindgoal.domain.chat.controller;

import com.mindgoal.domain.chat.dto.ChatMessageRequest;
import com.mindgoal.domain.chat.dto.ChatMessageResponse;
import com.mindgoal.domain.chat.dto.ChatRoomResponse;
import com.mindgoal.domain.chat.service.ChatService;
import com.mindgoal.domain.user.entity.auth.PrincipalDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @GetMapping("/room-list")
    public ResponseEntity<List<ChatRoomResponse>> getMyChatRooms(@AuthenticationPrincipal PrincipalDetails user) {
        final List<ChatRoomResponse> chatRoomsResponse = chatService.getMyChatRooms(user.getId());
        return ResponseEntity.ok(chatRoomsResponse);
    }

    @MessageMapping("/message")
    public void createChatMessage(@RequestBody ChatMessageRequest chatMessageRequest,
                                                                 @AuthenticationPrincipal PrincipalDetails user) {
        final ChatMessageResponse chatMessageResponse = chatService.sendMessage(user.getId(), chatMessageRequest);
        messagingTemplate.convertAndSend("/sub/chat" + chatMessageResponse.chatRoomId(), chatMessageResponse);
    }
}

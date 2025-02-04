package com.mindgoal.domain.chat.controller;

import com.mindgoal.domain.chat.dto.ChatRoomRequest;
import com.mindgoal.domain.chat.dto.ChatRoomResponse;
import com.mindgoal.domain.chat.service.ChatService;
import com.mindgoal.domain.user.entity.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatRoomResponse> createChatRoom(@RequestBody ChatRoomRequest chatRoomRequest,
                                                           @AuthenticationPrincipal PrincipalDetails user) {
        final ChatRoomResponse chatRoomResponse = chatService.saveChatRoom(chatRoomRequest, user.getId());
        return ResponseEntity.ok(chatRoomResponse);
    }
}

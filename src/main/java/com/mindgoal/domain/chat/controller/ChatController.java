package com.mindgoal.domain.chat.controller;

import com.mindgoal.domain.chat.dto.ChatRoomRequest;
import com.mindgoal.domain.chat.dto.ChatRoomResponse;
import com.mindgoal.domain.chat.service.ChatService;
import com.mindgoal.domain.user.entity.auth.PrincipalDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping()
    public ResponseEntity<List<ChatRoomResponse>> getMyChatRooms(@AuthenticationPrincipal PrincipalDetails user) {
        final List<ChatRoomResponse> chatRoomsResponse = chatService.getMyChatRooms(user.getId());
        return ResponseEntity.ok(chatRoomsResponse);
    }
}

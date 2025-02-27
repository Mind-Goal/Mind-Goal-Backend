package com.mindgoal.domain.chat.controller;

import com.mindgoal.domain.chat.dto.ChatRoomRequest;
import com.mindgoal.domain.chat.dto.ChatRoomResponse;
import com.mindgoal.domain.chat.service.ChatRoomService;
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
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ChatRoomResponse> createRoom(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                       @RequestBody ChatRoomRequest createRoomRequest) {
        return ResponseEntity.ok(chatRoomService.createChatRoom(principalDetails.getId(), createRoomRequest));
    }
        @GetMapping("/room-list")
    public ResponseEntity<List<ChatRoomResponse>> getMyChatRooms(@AuthenticationPrincipal PrincipalDetails user) {
        final List<ChatRoomResponse> chatRoomsResponse = chatRoomService.getMyChatRooms(user.getId());
        return ResponseEntity.ok(chatRoomsResponse);
    }
}

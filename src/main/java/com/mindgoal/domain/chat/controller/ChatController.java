package com.mindgoal.domain.chat.controller;

import com.mindgoal.domain.chat.dto.ChatMessageRequest;
import com.mindgoal.domain.chat.dto.ChatMessageResponse;
import com.mindgoal.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/rooms/{roomId}/message")
    public void createChatMessage(@DestinationVariable("roomId") final Long roomId,
                                  @RequestBody ChatMessageRequest chatMessageRequest) {
        final ChatMessageResponse chatMessageResponse = chatService.sendMessage(chatMessageRequest.userId(),
                chatMessageRequest);
        messagingTemplate.convertAndSend("/queue/chat/rooms/" + roomId, chatMessageResponse);
    }
}

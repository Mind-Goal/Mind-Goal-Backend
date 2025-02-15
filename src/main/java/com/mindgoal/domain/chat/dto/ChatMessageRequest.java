package com.mindgoal.domain.chat.dto;

public record ChatMessageRequest(Long expertId, Long userId, String content) {
}

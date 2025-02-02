package com.mindgoal.domain.chat.repository;

import com.mindgoal.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByExpertIdAndUserId(Long expertId, Long userId);
}

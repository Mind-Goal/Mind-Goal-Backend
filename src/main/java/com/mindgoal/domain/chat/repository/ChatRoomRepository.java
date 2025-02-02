package com.mindgoal.domain.chat.repository;

import com.mindgoal.domain.chat.entity.ChatRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByExpertIdAndUserId(Long expertId, Long userId);
}

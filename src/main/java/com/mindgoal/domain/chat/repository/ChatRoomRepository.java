package com.mindgoal.domain.chat.repository;

import com.mindgoal.domain.chat.entity.ChatRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByExpertId(Long expertId);

    List<ChatRoom> findAllByUserId(Long userId);

    Optional<ChatRoom> findByExpertIdAndUserId(Long expertId, Long userId);
}

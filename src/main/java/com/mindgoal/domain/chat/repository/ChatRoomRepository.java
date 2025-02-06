package com.mindgoal.domain.chat.repository;

import com.mindgoal.domain.chat.entity.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByExpertIdAndUserId(Long expertId, Long userId);

    List<ChatRoom> findAllByExpertId(Long expertId);

    List<ChatRoom> findAllByUserId(Long userId);
}

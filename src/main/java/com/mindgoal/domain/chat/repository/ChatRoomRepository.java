package com.mindgoal.domain.chat.repository;

import com.mindgoal.domain.chat.entity.ChatRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByExpertId(Long expertId);

    List<ChatRoom> findAllByUserId(Long userId);

    @Query("SELECT c FROM ChatRoom c WHERE c.expertId = :expertId AND c.userId = :userId")
    ChatRoom findByExpertIdAndUserId(@Param("expertId") Long expertId, @Param("userId") Long userId);

}

package com.mindgoal.domain.chat.entity;

import com.mindgoal.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_ROOMS")
@Entity
@Getter
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column
    private Long userId;
    @Column
    private Long expertId;

    @Embedded
    private ChatRoomStatus chatRoomStatus;

    @Builder
    private ChatRoom(final Long id, final Long userId, final Long expertId, final ChatRoomStatus chatRoomStatus) {
        this.id = id;
        this.userId = userId;
        this.expertId = expertId;
        this.chatRoomStatus = chatRoomStatus;
    }

    public ChatRoom(final Long userId, final Long expertId, final ChatRoomStatus chatRoomStatus) {
        this(null, userId, expertId, chatRoomStatus);
    }

    public Long getHeadCount() {
        return chatRoomStatus.getHeadCount();
    }

    public LocalDate getLastMessageAt() {
        return chatRoomStatus.getLastMessageAt();
    }

    public boolean getIsActive() {
        return chatRoomStatus.getIsActive();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ChatRoom chatRoom = (ChatRoom) object;
        return Objects.equals(id, chatRoom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

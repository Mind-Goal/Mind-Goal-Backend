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
    private Long expertId;

    @Column
    private Long userId;

    @Embedded
    private ChatRoomStatus chatRoomStatus;

    @Builder
    private ChatRoom(final Long id, final Long expertId, final Long userId, final ChatRoomStatus chatRoomStatus) {
        this.id = id;
        this.expertId = expertId;
        this.userId = userId;
        this.chatRoomStatus = chatRoomStatus;
    }

    public ChatRoom(final Long expertId, final Long userId, final ChatRoomStatus chatRoomStatus) {
        this(null, expertId, userId, chatRoomStatus);
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

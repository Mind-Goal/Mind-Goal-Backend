package com.mindgoal.domain.chat.entity;

import com.mindgoal.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_MESSAGES")
@Entity
@Getter
public class ChatMessage extends BaseEntity {
    private final int DEFAULT_READ_COUNT = 1; // 추후 단체 컨설팅 기능이 생긴다면 수정해야하는 로직

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long chatRoomId;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "READ_COUNT")
    private int readCount;

    public ChatMessage(Long userId, Long chatRoomId, String content) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.content = content;
        this.readCount = DEFAULT_READ_COUNT;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ChatMessage message = (ChatMessage) object;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

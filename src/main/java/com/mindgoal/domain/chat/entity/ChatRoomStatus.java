package com.mindgoal.domain.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomStatus {
    @ColumnDefault("0")
    @Column(nullable = false)
    private Long headCount;

    @Column(name = "LAST_MESSAGE_AT")
    private LocalDate lastMessageAt;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Builder
    public ChatRoomStatus(final Long headCount, final LocalDate lastMessageAt, final Boolean isActive) {
        this.headCount = headCount;
        this.lastMessageAt = lastMessageAt;
        this.isActive = isActive;
    }

    public static ChatRoomStatus createDefaultStatus() {
        return new ChatRoomStatus(0L, null, true);
    }
}

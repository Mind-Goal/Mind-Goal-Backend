package com.mindgoal.domain.matching.entity;

import com.mindgoal.common.BaseEntity;
import jakarta.persistence.Column;
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
@Table(name = "MATCHINGS")
@Entity
@Getter
public class Matching extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "EXPERT_ID", nullable = false)
    private Long expertId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "REQUEST_MESSAGE")
    private String requestMessage;

    @Column(name = "MATCHED_AT")
    private LocalDate matchedAt;

    @Builder
    public Matching(Long id, Long userId, Long expertId, String status,
                    String requestMessage, LocalDate matchedAt) {
        this.id = id;
        this.userId = userId;
        this.expertId = expertId;
        this.status = status;
        this.requestMessage = requestMessage;
        this.matchedAt = matchedAt;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void updateMatchedAt(LocalDate matchedAt) {
        this.matchedAt = matchedAt;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Matching matching = (Matching) object;
        return Objects.equals(id, matching.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
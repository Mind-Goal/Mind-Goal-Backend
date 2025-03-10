package com.mindgoal.domain.matching.entity;

/**
 * 매칭 상태를 나타내는 열거형 클래스
 */
public enum MatchingStatus {
    PENDING("대기중"),
    ACCEPTED("수락됨"),
    REJECTED("거절됨"),
    COMPLETED("완료됨"),
    CANCELED("취소됨");

    private final String description;

    MatchingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
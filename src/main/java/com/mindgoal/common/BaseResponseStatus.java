package com.mindgoal.common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    // 200번대: 성공 응답
    SUCCESS(true, 200, "요청이 성공했습니다"),
    EXPERT_REGISTER_SUCCESS(true, 201, "전문가 등록이 성공했습니다"),
    EXPERT_DETAIL_SUCCESS(true, 200, "전문가 상세 조회에 성공하였습니다"),
    EXPERT_UPDATE_SUCCESS(true, 200, "전문가 정보 수정이 성공했습니다"),
    MATCHING_REQUEST_SUCCESS(true, 201, "매칭 요청이 성공했습니다"),
    MATCHING_CANCEL_SUCCESS(true, 200, "매칭 취소가 성공했습니다"),
    MATCHING_LIST_SUCCESS(true, 200, "매칭 목록 조회가 성공했습니다"),

    // 400번대: Request 오류
    BAD_REQUEST(false, 400, "잘못된 요청입니다"),
    UNAUTHORIZED(false, 401, "인증이 필요합니다"),
    UNAUTHORIZED_ACCESS(false, 403, "해당 리소스에 대한 권한이 없습니다"),
    NOT_FOUND(false, 404, "리소스를 찾을 수 없습니다"),
    METHOD_NOT_ALLOWED(false, 405, "허용되지 않은 메서드입니다"),
    EXPERT_ALREADY_EXISTS(false, 409, "이미 등록된 전문가입니다"),
    EXPERT_NOT_FOUND(false, 404, "전문가를 찾을 수 없습니다"),
    MATCHING_NOT_FOUND(false, 404, "매칭 정보를 찾을 수 없습니다"),
    MATCHING_ALREADY_CANCELED(false, 400, "이미 취소된 매칭입니다"),
    EXPIRED_ACCESS_TOKEN(false, 404, "만료된 토큰입니다."),
    UNSUPPORTED(false, 404, "지원하지 않는 토큰입니다."),
    TOKEN_NOT_FOUND(false, 404, "존재하지 않는 토큰입니다."),

    // 500번대: Server 오류
    INTERNAL_SERVER_ERROR(false, 500, "서버 내부 오류가 발생했습니다"),
    SERVICE_UNAVAILABLE(false, 503, "서비스를 사용할 수 없습니다");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
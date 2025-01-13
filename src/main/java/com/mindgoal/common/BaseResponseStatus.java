package com.mindgoal.common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    // 200번대: 성공 응답
    SUCCESS(true, 200, "요청이 성공했습니다"),

    // 400번대: Request 오류
    BAD_REQUEST(false, 400, "잘못된 요청입니다"),
    UNAUTHORIZED(false, 401, "인증이 필요합니다"),
    NOT_FOUND(false, 404, "리소스를 찾을 수 없습니다"),
    METHOD_NOT_ALLOWED(false, 405, "허용되지 않은 메서드입니다"),

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
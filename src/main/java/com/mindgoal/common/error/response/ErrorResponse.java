package com.mindgoal.common.error.response;

import com.mindgoal.common.error.ErrorCode;
import lombok.Builder;

@Builder
public record ErrorResponse(String code, String message, int status) {
    public static ErrorResponse from(final ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .build();
    }
}

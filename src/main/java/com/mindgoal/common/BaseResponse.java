package com.mindgoal.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    private final int status;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp;

    public static <T> BaseResponse<T> success(T data, String message) {
        return new BaseResponse<>(
                BaseResponseStatus.SUCCESS.getCode(),
                message,
                data,
                LocalDateTime.now()
        );
    }

    public static <T> BaseResponse<T> of(BaseResponseStatus status, T data) {
        return new BaseResponse<>(
                status.getCode(),
                status.getMessage(),
                data,
                LocalDateTime.now()
        );
    }

    public static <T> BaseResponse<T> error(BaseResponseStatus status) {
        return new BaseResponse<>(
                status.getCode(),
                status.getMessage(),
                null,
                LocalDateTime.now()
        );
    }

    public static <T> BaseResponse<T> error(BaseResponseStatus status, String customMessage) {
        return new BaseResponse<>(
                status.getCode(),
                customMessage,
                null,
                LocalDateTime.now()
        );
    }
}
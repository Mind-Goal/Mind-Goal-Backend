package com.mindgoal.exception;

import com.mindgoal.common.BaseResponseStatus;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final BaseResponseStatus status;

    public CustomException(BaseResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}

package com.mindgoal.common.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String CODE_PREFIX = "MG";

    String getCode();
    String name();
    HttpStatus getHttpStatus();
    String getMessage();
}

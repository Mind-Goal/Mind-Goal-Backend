package com.mindgoal.common.error.handler;

import com.mindgoal.common.error.ErrorCode;
import com.mindgoal.common.error.GlobalErrorCode;
import com.mindgoal.common.error.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(handleExceptionInternal(GlobalErrorCode.TOKEN_NOT_FOUND));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> handleMalformedJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(GlobalErrorCode.INVALID_TOKEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(GlobalErrorCode.EXPIRED_TOKEN);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.from(errorCode));
    }
}

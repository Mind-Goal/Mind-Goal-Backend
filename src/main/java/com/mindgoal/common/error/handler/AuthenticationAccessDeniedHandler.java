package com.mindgoal.common.error.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindgoal.common.error.GlobalErrorCode;
import com.mindgoal.common.error.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(final HttpServletRequest request,
                       final HttpServletResponse response,
                       final AccessDeniedException accessDeniedException) throws IOException {
        final String accept = request.getHeader(HttpHeaders.ACCEPT);
        if (MediaType.APPLICATION_JSON_VALUE.equals(accept)) {
            final GlobalErrorCode errorCode = GlobalErrorCode.RESOURCE_NOT_FOUND;
            response.setStatus(errorCode.getHttpStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(CharEncoding.UTF_8);

            final ErrorResponse errorResponse = ErrorResponse.from(errorCode);
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }
}

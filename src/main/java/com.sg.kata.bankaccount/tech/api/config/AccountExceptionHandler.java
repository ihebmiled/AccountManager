package org.account.manager.tech.api.config;


import lombok.extern.slf4j.Slf4j;
import org.account.manager.business.execption.BusinessException;
import org.account.manager.tech.api.exception.BadDataArgumentException;
import org.account.manager.tech.api.io.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
@Slf4j
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadDataArgumentException.class})
    protected ResponseEntity<Object> handleBadDataArgumentException(BadDataArgumentException ex, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        return buildErrorResponse(ex, ex.getErrorCode().name(), ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({BusinessException.class})
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        return buildErrorResponse(ex, ex.getErrorCode().name(), ex.getMessage(), HttpStatus.PRECONDITION_FAILED, request);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        return buildErrorResponse(ex, "TECHNICAL_ERROR", "An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception ex, String code, String message, HttpStatus httpStatus, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        errorResponse.setTimeStamp(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        log.warn(ex.getMessage(), ex);
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), httpStatus, request);
    }
}

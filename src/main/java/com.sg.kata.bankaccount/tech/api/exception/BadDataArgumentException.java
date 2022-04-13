package org.account.manager.tech.api.exception;

import lombok.Getter;

public class BadDataArgumentException extends RuntimeException {
    @Getter
    private final BadParameterErrorCode errorCode;

    public BadDataArgumentException(BadParameterErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}

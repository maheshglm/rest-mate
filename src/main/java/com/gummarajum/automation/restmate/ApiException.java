package com.gummarajum.automation.restmate;

import org.slf4j.helpers.MessageFormatter;

public class ApiException extends RuntimeException {
    private final ApiExceptionType apiExceptionType;

    public ApiException(ApiExceptionType apiExceptionType, String message, Object... args) {
        super(MessageFormatter.arrayFormat(message, args).getMessage());
        this.apiExceptionType = apiExceptionType;
    }

    public ApiException(Throwable throwable, ApiExceptionType apiExceptionType, String message, Object... args) {
        super(MessageFormatter.arrayFormat(message, args).getMessage(), throwable);
        this.apiExceptionType = apiExceptionType;
    }

    public ApiException(ApiExceptionType apiExceptionType, Throwable throwable) {
        super(throwable);
        this.apiExceptionType = apiExceptionType;
    }

    public ApiExceptionType getApiExceptionType() {
        return apiExceptionType;
    }
}

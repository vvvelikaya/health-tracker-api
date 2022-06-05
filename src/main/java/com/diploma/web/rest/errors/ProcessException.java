package com.diploma.web.rest.errors;

import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString(callSuper = true)
public class ProcessException extends RuntimeException {
    private final HttpStatus statusCode;
    private final String message;

    public ProcessException(String message, HttpStatus statusCode) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package com.application.rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {

    public BadRequestException(String message) {
        this(message, null);
    }

    public BadRequestException(String message, Exception exception) {
        super(HttpStatus.BAD_REQUEST, message, exception);
    }
}

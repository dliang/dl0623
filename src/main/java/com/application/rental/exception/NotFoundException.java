package com.application.rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    public NotFoundException(String message) {
        this(message, null);
    }

    public NotFoundException(String message, Exception exception) {
        super(HttpStatus.NOT_FOUND, message, exception);
    }
}

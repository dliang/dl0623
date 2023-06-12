package com.application.rental.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@RequiredArgsConstructor
public class ServerError {
    private final HttpStatusCode httpStatus;
    private final String message;
}

package com.application.rental.controller;

import com.application.rental.exception.ServerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.function.Supplier;

@RestControllerAdvice
public class AbstractController extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    protected  <T> T runWithErrorhandling(Supplier<T> supplier){
        try {
            return supplier.get();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerError> handleException(Exception ex) {
        if (ex instanceof ResponseStatusException responseException) {
            var serverError = new ServerError(responseException.getStatusCode(), responseException.getMessage());
            return new ResponseEntity<>(serverError, serverError.getHttpStatus());
        } else {
            return new ResponseEntity<>(new ServerError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

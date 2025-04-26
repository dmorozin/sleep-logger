package com.noom.interview.fullstack.sleep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(buildExceptionDetails(exception, request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceInsertException.class)
    public ResponseEntity<ExceptionDetails> sleepLogInsertHandling(ResourceInsertException exception, WebRequest request) {
        return new ResponseEntity<>(buildExceptionDetails(exception, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DatabaseFetchException.class)
    public ResponseEntity<ExceptionDetails> databaseFetchHandling(DatabaseFetchException exception, WebRequest request) {
        return new ResponseEntity<>(buildExceptionDetails(exception, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ExceptionDetails buildExceptionDetails(Exception ex, WebRequest request) {
        return new ExceptionDetails(
                LocalDateTime.now().toString(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

}

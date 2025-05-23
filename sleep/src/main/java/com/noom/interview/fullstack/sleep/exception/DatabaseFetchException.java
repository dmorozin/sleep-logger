package com.noom.interview.fullstack.sleep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseFetchException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DatabaseFetchException(String message) {

        super(message);
    }
}

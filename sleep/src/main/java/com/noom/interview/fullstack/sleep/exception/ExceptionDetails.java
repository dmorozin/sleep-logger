package com.noom.interview.fullstack.sleep.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionDetails {

    private String timestamp;
    private String message;
    private String details;
}

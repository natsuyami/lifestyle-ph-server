package com.lifestyle.ph.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class VerificationException extends RuntimeException {
    
    public VerificationException(String message) {
        super(message);
    }
}

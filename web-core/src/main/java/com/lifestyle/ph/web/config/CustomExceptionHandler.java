package com.lifestyle.ph.web.config;

import com.lifestyle.ph.common.exception.DataResponse;
import com.lifestyle.ph.common.exception.VerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<DataResponse> handleException(Exception e) {
        DataResponse dataResponse = new DataResponse(e.getMessage());
        return new ResponseEntity<>(dataResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(VerificationException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ResponseEntity<DataResponse> handleResourceNotFoundException(VerificationException e) {
        DataResponse dataResponse = new DataResponse(e.getMessage());
        return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
    }
}

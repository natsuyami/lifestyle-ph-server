package com.lifestyle.ph.web.builder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder<T> {
    private HttpStatus status;
    private T body;
    
    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder<>();
    }
    
    public ResponseBuilder<T> status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseBuilder<T> body(T body) {
        this.body = body;
        return this;
    }

    public ResponseEntity<T> build() {
        return ResponseEntity.status(status).body(body);
    }

    public ResponseEntity<T> success(T body) {
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public ResponseEntity<T> error(T body) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}

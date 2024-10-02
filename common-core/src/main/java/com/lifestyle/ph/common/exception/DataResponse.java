package com.lifestyle.ph.common.exception;

import com.lifestyle.ph.common.constant.ErrorMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    public DataResponse(T data) {
        this.data = data;
        this.code = null;
        this.message = null;
    }
    
    public DataResponse(String code) {
        this.data = null;
        this.code = code;
        this.message = ErrorMessage.messageOfCode(code).getMessage();
    }

    public DataResponse(String code, T data) {
        this.code = code;
        this.message = ErrorMessage.messageOfCode(code).getMessage();
        this.data = data;
    }
}

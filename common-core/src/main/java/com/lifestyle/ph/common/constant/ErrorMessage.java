package com.lifestyle.ph.common.constant;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    
    USER_NOT_PAID("user_not_paid", "the user is not yet paid");
    
    private String code;
    private String message;
    
    ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public static ErrorMessage messageOfCode(String code) {
        for (ErrorMessage errorMessage : ErrorMessage.values()) {
            if (errorMessage.getCode().equals(code)) {
                return errorMessage;
            }
        }
        
        return null;
    }
    
}

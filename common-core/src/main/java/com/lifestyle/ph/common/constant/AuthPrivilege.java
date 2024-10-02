package com.lifestyle.ph.common.constant;

import lombok.Getter;

@Getter
public enum AuthPrivilege {
    
    EMAIL("email"),
    PROFILE("profile"),
    OPENID("openid"),
    GET_METHOD("GET"),
    POST_METHOD("POST"),
    OPTION_METHOD("OPTIONS"),
    MAPPING_CORS("/**");
        
    private String desc;

    AuthPrivilege() {}
    
    AuthPrivilege(String desc) {
        this.desc = desc;    
    }
}

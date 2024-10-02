package com.lifestyle.ph.common.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String sub;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String email;
    private boolean emailVerified;
    private String locale;
}

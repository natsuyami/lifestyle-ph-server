package com.lifestyle.ph.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CredentialDTO {
    
    private String accessToken;
    private String refreshToken;
    private String userName;
    private String status;
}

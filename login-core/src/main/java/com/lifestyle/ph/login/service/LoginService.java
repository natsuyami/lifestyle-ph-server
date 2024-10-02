package com.lifestyle.ph.login.service;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.lifestyle.ph.common.builder.DTOBuilder;
import com.lifestyle.ph.common.constant.AuthPrivilege;
import com.lifestyle.ph.common.dto.CredentialDTO;
import com.lifestyle.ph.common.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    
    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientSecret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.domain.redirect.uri}")
    private String redirectUrl;

    public String gmailAuthPage() {
        LOGGER.info("Gmail Authentication Page Initialized, using clientId={}", clientId);
        
        String url = new GoogleAuthorizationCodeRequestUrl(clientId,
                redirectUrl,
                Arrays.asList(
                        AuthPrivilege.EMAIL.getDesc(),
                        AuthPrivilege.OPENID.getDesc(), 
                        AuthPrivilege.PROFILE.getDesc()))
                .setAccessType("offline")
                .build();
        return url;
    }

    public CredentialDTO gmailAccessToken(String code) {
        LOGGER.info("Gmail generating access token, using code={}", code);

        GoogleIdToken userDetails;
        GoogleTokenResponse response;
        try {
            response = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(), new GsonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    redirectUrl
            ).execute();

            userDetails = response.parseIdToken();

            return CredentialDTO.builder()
                    .accessToken(response.getAccessToken())
                    .refreshToken(response.getRefreshToken())
                    .userName(userDetails.getPayload().getEmail())
                    .status(null)
                    .build();
        } catch (Exception e) {
            LOGGER.error("Failed to Generate Access Token, error: ", e);
            throw new VerificationException("Cannot Generate Access Token");
        }
    }

    public CredentialDTO refreshToken(CredentialDTO credentialDTO) {
        GoogleRefreshTokenRequest refreshTokenRequest = new GoogleRefreshTokenRequest(
                new NetHttpTransport(),
                new GsonFactory(),
                credentialDTO.getRefreshToken(),
                clientId,
                clientSecret
        );

        try {
            TokenResponse response = refreshTokenRequest.execute();
            GoogleTokenResponse googleToken = new GoogleTokenResponse().setAccessToken(response.getAccessToken())
                    .setRefreshToken(response.getRefreshToken())
                    .setExpiresInSeconds(response.getExpiresInSeconds())
                    .setTokenType(response.getTokenType());

            return CredentialDTO.builder()
                    .accessToken(googleToken.getAccessToken())
                    .refreshToken(googleToken.getRefreshToken())
                    .userName(googleToken.parseIdToken().getPayload().getEmail())
                    .status("")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String gmailLogout() {
        return "Logout, Spring Boot!";
    }
}

package com.lifestyle.ph.common.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientBuilder {
    
    private static final String BEARER = "Bearer ";

    public WebClient getWebClient(String accessToken, String domainUrl) {
        return WebClient.builder()
                .baseUrl(domainUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, BEARER.concat(accessToken))
                .build();
    }
}

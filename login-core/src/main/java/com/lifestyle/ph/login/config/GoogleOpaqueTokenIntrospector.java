package com.lifestyle.ph.login.config;

import com.lifestyle.ph.common.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class GoogleOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final WebClient userInfoClient;
    
    private static final String ATTRIB_SUB = "sub";
    private static final String ATTRIB_NAME = "name";
    private static final String ATTRIB_TOKEN = "access_token";

    private static final String PATH = "/oauth2/v3/userinfo";

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        UserDTO userDTO = userInfoClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path(PATH)
                        .queryParam(ATTRIB_TOKEN, token)
                        .build())
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(ATTRIB_SUB, userDTO.getSub());
        attributes.put(ATTRIB_NAME, userDTO.getName());
        return new OAuth2IntrospectionAuthenticatedPrincipal(userDTO.getName(), attributes, null);
    }
}

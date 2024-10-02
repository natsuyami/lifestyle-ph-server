package com.lifestyle.ph.login.config;

import com.lifestyle.ph.common.constant.AuthPrivilege;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    
    @Value("${spring.security.oauth2.client.domain.url}")
    private String clientDomain;

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(AuthPrivilege.MAPPING_CORS.getDesc())
                        .allowedMethods(AuthPrivilege.GET_METHOD.getDesc(), 
                                AuthPrivilege.OPTION_METHOD.getDesc(), 
                                AuthPrivilege.POST_METHOD.getDesc())
                        .allowedOrigins(clientDomain);
            }
        };
    }
}
package com.lifestyle.ph.video.config;

import com.clickntap.vimeo.Vimeo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class VimeoConfig {
    @Value("${vimeo.access.token}")
    private String accessToken;

    @Bean
    public Vimeo vimeo() {
        return new Vimeo(accessToken);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

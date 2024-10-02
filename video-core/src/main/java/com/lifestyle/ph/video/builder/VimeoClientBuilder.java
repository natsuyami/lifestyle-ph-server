package com.lifestyle.ph.video.builder;

import com.lifestyle.ph.common.builder.WebClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VimeoClientBuilder extends WebClientBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(VimeoClientBuilder.class);

    @Value("${vimeo.api.get.all.videos.uri}")
    private String getAllVideos;

    @Value("${vimeo.api.get.video.uri}")
    private String getVideo;

    @Value("${vimeo.access.token}")
    private String accessToken;

    @Value("${vimeo.api.domain.url}")
    private String domainUrl;
    
    public String getVimeoVideos() {
        String response = getWebClient(accessToken, domainUrl).get()
                .uri(getAllVideos)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // block to wait for the response, should be avoided in production

        return response;
    }

    public String getVimeoSpecificVideo(String videoId) {
        String response = getWebClient(accessToken, domainUrl).get()
                .uri(getVideo, videoId)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // block to wait for the response, should be avoided in production

        return response;
    }
}

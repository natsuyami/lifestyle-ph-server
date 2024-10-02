package com.lifestyle.ph.video.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.lifestyle.ph.common.dto.VideoDTO;
import com.lifestyle.ph.common.utils.JsonStringParserUtil;
import com.lifestyle.ph.video.builder.VimeoClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VimeoService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VimeoService.class);

    @Value("${vimeo.access.token}")
    private String accessToken;
    
    @Autowired
    private VimeoClientBuilder client;

    public List<VideoDTO> getAllVideos() {
        LOGGER.info("Initialized getting all videos from vimeo");        
        
        List<VideoDTO> videos = new ArrayList<>();
        for (JsonNode videoNode : JsonStringParserUtil.extractJsonNodeList(client.getVimeoVideos())) {
            videos.add(setVideoDTO(videoNode));
        }
        
        return videos;
    }

    public VideoDTO getVideo(String videoId) {
        LOGGER.info("Initialized getting video with id={} from vimeo", videoId);
        
        JsonNode rootNode = JsonStringParserUtil.extractJsonNode(client.getVimeoSpecificVideo(videoId));
        
        return setVideoDTO(rootNode);
    }
    
    private VideoDTO setVideoDTO(JsonNode rootNode) {
        LOGGER.info("Setting of video with name={}", rootNode.get("name").asText());

        VideoDTO video = new VideoDTO();
        video.setUri(rootNode.get("uri").asText());
        video.setTitle(rootNode.get("name").asText());
        video.setUrl(rootNode.get("link").asText());
        video.setId(rootNode.get("uri").asText().substring(8));
        
        return video;
    }
}

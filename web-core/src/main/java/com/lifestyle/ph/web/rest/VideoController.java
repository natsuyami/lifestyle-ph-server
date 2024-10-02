package com.lifestyle.ph.web.rest;

import com.lifestyle.ph.common.dto.VideoDTO;
import com.lifestyle.ph.video.service.VimeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/videos", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class VideoController {

    @Autowired
    private VimeoService vimeoService;

    @GetMapping("/all")
        public List<VideoDTO> getExample() {
        // Your logic here
        try {
            return vimeoService.getAllVideos();
        } catch (Exception e) {
            System.out.println("Failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @GetMapping("/video/{videoId}")
    public VideoDTO getExample(@PathVariable String videoId) {
        // Your logic here
        try {
            return vimeoService.getVideo(videoId);
        } catch (Exception e) {
            System.out.println("Failed: " + e.getMessage());
            return new VideoDTO();
        }
    }

    @GetMapping("/home")
    public String home() {
        // Your logic here
        return "testing";
    }
}

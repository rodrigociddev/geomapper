package com.example.geodemo.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final com.example.geodemo.media.MediaService mediaService;

    @Autowired
    public MediaController(com.example.geodemo.media.MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/rename/{oldName}/{newName}")
    public String renameMedia(@PathVariable String oldName, @PathVariable String newName) {
        return mediaService.renameMedia(oldName, newName);
    }

    @PostMapping("/annotate/{mediaName}/{annotation}")
    public String addAnnotations(@PathVariable String mediaName, @PathVariable String annotation) {
        return mediaService.addAnnotations(mediaName, annotation);
    }
}


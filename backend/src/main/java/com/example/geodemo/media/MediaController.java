package com.example.geodemo.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * RestController for operations on Media objects
 * Calls methods in mediaService
 */
@RestController
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PatchMapping("/rename/{oldName}")
    public String renameMedia(@PathVariable String oldName, @RequestParam String newName) {
        return mediaService.renameMedia(oldName, newName);
    }

    @PatchMapping("/annotate/{mediaName}")
    public String addAnnotations(@PathVariable String mediaName, @RequestBody String annotation) {
        return mediaService.addAnnotations(mediaName, annotation);
    }

    @PatchMapping("/longitude/{mediaName}")
    public String addLong(@PathVariable String mediaName, @RequestBody double longitude) {
        return mediaService.addLong(mediaName, longitude);
    }

    @PatchMapping("/latitude/{mediaName}")
    public String addLat(@PathVariable String mediaName, @RequestBody double latitude) {
        return mediaService.addLat(mediaName, latitude);
    }
}


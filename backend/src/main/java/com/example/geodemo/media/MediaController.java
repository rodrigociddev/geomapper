package com.example.geodemo.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PatchMapping("/rename/{id}")
    public String renameMedia(@PathVariable String id, @RequestParam String newName) {
        return mediaService.renameMedia(id, newName);
    }

    @PatchMapping("/annotate/{id}")
    public String addAnnotations(@PathVariable String id, @RequestParam String annotation) {
        return mediaService.addAnnotations(id, annotation);
    }

    @PatchMapping("/longitude/{id}")
    public String addLong(@PathVariable String id, @RequestBody double longitude) {
        return mediaService.addLong(id, longitude);
    }

    @PatchMapping("/latitude/{id}")
    public String addLat(@PathVariable String id, @RequestBody double latitude) {
        return mediaService.addLat(id, latitude);
    }

    @PatchMapping("/changeAll/{id}")
    public ResponseEntity<Media> changeAll (@PathVariable String id, @RequestBody Media updated){
        try{
            Media media = changeAll(id,updated).getBody();

            return ResponseEntity.ok(media);
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
}


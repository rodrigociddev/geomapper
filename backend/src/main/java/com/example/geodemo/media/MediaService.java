package com.example.geodemo.media;

import com.example.geodemo.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.geodemo.project.ProjectService.project;

/**
 * Methods for manipulating Media objects
 * Used by MediaController
 */
@Service
public class MediaService {

    private final ProjectService projectService;
     @Autowired
    public MediaService(ProjectService projectService){
        this.projectService = projectService;
    }
    public String renameMedia(String oldName, String newName){
        Media media = projectService.searchMedia(oldName);

        if(media != null){
            project.deleteMedia(media);
            media.setName(newName);
            project.addMedia(media);
            return "Media name succesfully changed";
        }

        return "Media name cannot be changed";
    }

    public String addAnnotations(String mediaName,String annotation){
        Media media = projectService.searchMedia(mediaName);

        if(media != null){
            project.deleteMedia(media);
            media.setAnnotations(annotation);
            project.addMedia(media);
            return "Media succesfully annotated";
        }

        return "Media cannot be annotated";
    }
    }

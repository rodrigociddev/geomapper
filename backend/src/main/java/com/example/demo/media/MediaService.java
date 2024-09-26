package com.example.geodemo.media;

import com.example.geodemo.project.ProjectService;
import org.springframework.stereotype.Service;

import static com.example.geodemo.project.ProjectService.project;

@Service
public class MediaService {

    private static ProjectService search = new ProjectService();


    public String renameMedia(String oldName, String newName){
        Media media = search.searchMedia(oldName);

        if(media != null){
            project.deleteMedia(media);
            media.setName(newName);
            project.addMedia(media);
            return "Media name succesfully changed";
        }

        return "Media name cannot be changed";
    }

    public String addAnnotations(String mediaName,String annotation){
        Media media = search.searchMedia(mediaName);

        if(media != null){
            project.deleteMedia(media);
            media.setAnnotations(annotation);
            project.addMedia(media);
            return "Media succesfully annotated";
        }

        return "Media cannot be annotated";
    }
    }

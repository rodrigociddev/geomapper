package com.example.geodemo.media;

import com.example.geodemo.exceptions.exception.MediaAlreadyExistsException;
import com.example.geodemo.exceptions.exception.MediaNotFoundException;
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

    public String renameMedia(String oldName, String newName) {
        Media media = projectService.searchMedia(oldName);
        if (projectService.searchMedia(newName) != null) {
            System.out.println("Media already exists exception");
            throw new MediaAlreadyExistsException(newName);
        }
        if (media == null) {
            System.out.println("Media named " + oldName + " does not exist");
            throw new MediaNotFoundException(oldName);
        }

        //defining the directory for user media files
        String workingDir = System.getProperty("user.dir");
        File oldFile = new File(workingDir + File.separator + "userMedia" + File.separator + oldName);
        File newFile = new File(workingDir + File.separator + "userMedia" + File.separator + newName);

        if (oldFile.exists()) {
            boolean renamed = oldFile.renameTo(newFile);
            if (!renamed) {
                throw new RuntimeException("Failed to rename file on the filesystem.");
            }
        } else {
            System.out.println("File not found in userMedia folder: " + oldName);
            throw new MediaNotFoundException(oldName);
        }

        project.deleteMedia(media);
        media.setName(newName);
        project.addMedia(media);

        System.out.println("Changed " + oldName + " into " + newName);
        return "Media name successfully changed";
    }

    public String addAnnotations(String mediaName,String annotation){
        Media media = projectService.searchMedia(mediaName);

        if(media != null){
            project.deleteMedia(media);
            media.setAnnotations(annotation);
            project.addMedia(media);
            System.out.println("Media succesfully annotated");
            return "Media succesfully annotated";
        }
        throw new MediaNotFoundException(mediaName);

    }

    public String addLong(String mediaName,double longitude){
        Media media = projectService.searchMedia(mediaName);

        if(media != null){
            project.deleteMedia(media);
            media.setLongitude(longitude);
            project.addMedia(media);
            System.out.println("Media succesfully changed longitude");
            return "Media succesfully changed longitude";
        }

        throw new MediaNotFoundException(mediaName);
    }

    public String addLat(String mediaName,double latitude){
        Media media = projectService.searchMedia(mediaName);

        if(media != null){
            project.deleteMedia(media);
            media.setLatitude(latitude);
            project.addMedia(media);
            System.out.println("Media succesfully changed latitude");
            return "Media succesfully changed latitude";
        }

        throw new MediaNotFoundException(mediaName);
    }
}

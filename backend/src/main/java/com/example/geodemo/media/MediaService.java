package com.example.geodemo.media;

import com.example.geodemo.exceptions.exception.MediaAlreadyExistsException;
import com.example.geodemo.exceptions.exception.MediaNotFoundException;
import com.example.geodemo.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.geodemo.project.ProjectService.project;

import java.io.File;


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

    public String renameMedia(String id, String newName) {
        Media media = projectService.searchMedia(id);
        if (projectService.searchMedia(newName) != null) {
            System.out.println("Media already exists exception");
            throw new MediaAlreadyExistsException(newName);
        }
        if (media == null) {
            System.out.println("Media with ID " + id + " does not exist");
            throw new MediaNotFoundException(id);
        }

        String oldName = media.getTitle();

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
        media.setTitle(newName);
        project.addMedia(media);

        System.out.println("Changed " + oldName + " into " + newName);
        return "Media name successfully changed";
    }

    public String addAnnotations(String id,String annotation){
        Media media = projectService.searchMedia(id);

        if(media != null){
            project.deleteMedia(media);
            media.setAnnotations(annotation);
            project.addMedia(media);
            System.out.println("Media succesfully annotated");
            return "Media succesfully annotated";
        }
        throw new MediaNotFoundException(id);

    }

    public String addLong(String id,double longitude){
        Media media = projectService.searchMedia(id);

        if(media != null){
            project.deleteMedia(media);
            media.setLongitude(longitude);
            project.addMedia(media);
            System.out.println("Media succesfully changed longitude");
            return "Media succesfully changed longitude";
        }

        throw new MediaNotFoundException(id);
    }

    public String addLat(String id,double latitude){
        Media media = projectService.searchMedia(id);

        if(media != null){
            project.deleteMedia(media);
            media.setLatitude(latitude);
            project.addMedia(media);
            System.out.println("Media succesfully changed latitude");
            return "Media succesfully changed latitude";
        }

        throw new MediaNotFoundException(id);
    }

    public void updateMedia(String id, Media updates){
        Media media = projectService.searchMedia(id);
        if (media == null) {
            throw new MediaNotFoundException(id);
        }

        String oldName = media.getTitle();
        String newName = updates.getTitle();

        //defining the directory for user media files
//        String workingDir = System.getProperty("user.dir");
//        File oldFile = new File(workingDir + File.separator + "userMedia" + File.separator + oldName);
//        File newFile = new File(workingDir + File.separator + "userMedia" + File.separator + newName);

//        if (oldFile.exists()) {
//            boolean renamed = oldFile.renameTo(newFile);
//            if (!renamed) {
//                throw new RuntimeException("Failed to rename file on the filesystem.");
//            }
//        } else {
//            System.out.println("File not found in userMedia folder: " + oldName);
//            throw new MediaNotFoundException(oldName);
//        }
//
//        project.deleteMedia(media);

        if (updates.getTitle() != null) {
            media.setTitle(updates.getTitle());
        }
        if (updates.getAnnotations() != null) {
            media.setAnnotations(updates.getAnnotations());
        }
        if (updates.getLatitude() != 0) {
            media.setLatitude(updates.getLatitude());
        }
        if (updates.getLongitude() != 0) {
            media.setLongitude(updates.getLongitude());
        }

//        project.addMedia(media);

//        return media;
    }
}

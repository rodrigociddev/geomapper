package com.example.geodemo.project;

import com.example.geodemo.media.Media;
import com.example.geodemo.metaDataExtraction.Extractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectService {
    private static Extractor extractor = new Extractor();
    //private static Map<String, Media> globalLookUp = new HashMap<>();
    public static Project project;

    @Autowired
    public ProjectService(Project project) {
        this.project =project;
    }

    //uploads file to project instance
    public  String uploadFile(MultipartFile file) {
        if (file != null) {
            // Log file information
            System.out.println("Received file:");
            System.out.println("Filename: " + file.getOriginalFilename());
            System.out.println("Size: " + file.getSize() + " bytes");
            System.out.println("Content-Type: " + file.getContentType());
        } else {
            return "No file uploaded";
        }

        try (InputStream inputStream = file.getInputStream()) {
            Media media = extractor.extractMetadata(inputStream, file.getOriginalFilename());

            if (media != null) {

                if (project.containsMedia(media.getName())) {
                    return "Media named: " + media.getName() + " is already part of the project";
                } else {
                    project.addMedia(media);
                }

               // if (!globalLookUp.containsKey(media.getName())) {
              //      globalLookUp.put(media.getName(), media);
               // }

                return "File processed successfully, added to project";
            } else {
                return "No GPS data found in the file";
            }
        } catch (Exception e) {
            return "Error processing the file: " + e.getMessage();
        }
    }



    public String renameProject(String name){
        project.setName(name);
        return "Project name has been successfully named to" + project.getName();
    }


    // ordered by most recently added/modified
    public void mediaOrder() {
        System.out.println("Media List:");
        int order = 0;
        for (int i = project.getProjectList().size() - 1; i >= 0; i--) {
            Media media = project.getProjectList().get(i);
            order +=1;
            System.out.println( order + ". Media Name: " + media.getName());
        }
    }

    //searches the media in a project, returns media object w featues of the media
    public Media searchMedia(String name) {
        try {
            //Media media = globalLookUp.get(name);
            Media media = project.getMediaByName(name);
            if (media == null) {
                System.out.println("Media named: " + name + " does not exist");
            }
            return media;
        } catch (Exception e) {
            System.out.println("Error searching for media: " + e.getMessage());
            return null;
        }
    }


    public String deleteMedia(String name){
        Media media = searchMedia(name);
        if (media != null ) {
            project.deleteMedia(media);
            return "Media deleted successfully.";
        } else {
            return "Media not found";
        }
    }
}

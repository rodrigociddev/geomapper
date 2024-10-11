package com.example.geodemo.project;

import com.example.geodemo.media.Media;

import com.example.geodemo.metaDataExtractor.Extractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectService {
    private static Extractor extractor = new Extractor();
    //private static Map<String, Media> globalLookUp = new HashMap<>();
    public static Project project;

    @Autowired
    public ProjectService(Project project) {
        ProjectService.project =project;
    }

    //uploads file to project instance
    public String uploadFile(MultipartFile file) {
        if (file != null) {
            try (InputStream inputStream = file.getInputStream()) {
                boolean isMulti = true;
                return processMedia(inputStream, file.getOriginalFilename(), file.getSize(), file.getContentType(), isMulti, file);
            } catch (Exception e) {
                return "Error processing the file: " + e.getMessage();
            }
        } else {
            return "No file uploaded";
        }
    }

    public String addFile(String filePath) {
        Path mediaPath = Paths.get(filePath);
        if (Files.exists(mediaPath) && !Files.isDirectory(mediaPath)) {
            try {
                long fileSize = Files.size(mediaPath);
                String contentType = Files.probeContentType(mediaPath);
                try (InputStream inputStream = Files.newInputStream(mediaPath)) {
                    boolean isMulti = false;
                    return processMedia(inputStream, mediaPath.getFileName().toString(), fileSize, contentType, isMulti, filePath);
                }
            } catch (Exception e) {
                return "Error processing file: " + e.getMessage();
            }
        } else {
            return "No valid file at: " + filePath;
        }
    }

    //helper method to extract data from file and process it
    private String processMedia(InputStream inputStream, String fileName, long size, String contentType, boolean isMultipart, Object fileSource) throws IOException {

        System.out.println("Received file:");
        System.out.println("Filename: " + fileName);
        System.out.println("Size: " + size + " bytes");
        System.out.println("Content-Type: " + contentType);

        // extract media metadata
        Media media = extractor.extractMetadata(inputStream, fileName);
        if (media != null) {
            if (project.containsMedia(media.getName())) {
                return "Media named: " + media.getName() + " is already part of the project";
            } else {
                project.addMedia(media);

                // this code is to save the media based on whether its a file or multipartFile
                if (isMultipart && fileSource instanceof MultipartFile) {
                    saveMedia((MultipartFile) fileSource);
                } else if (fileSource instanceof String) {
                    saveMedia((String) fileSource);
                }

                return "File processed successfully, added to project";
            }
        } else {
            return "No GPS data found in the file";
        }
    }



    public String renameProject(String name){
        project.setName(name);
        System.out.println("Project name has been successfully named to " + project.getName());
        return "Project name has been successfully named to " + project.getName();
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

    //searches the media in a project, returns media object w features of the media
    public Media searchMedia(String name) {
        try {
            //Media media = globalLookUp.get(name);
            Media media = project.getMediaByName(name);
            if (media == null) {
                System.out.println("Media named: " + name + " does not exist");
            }
            System.out.println("Found " + media.getName());
            return media;
        } catch (Exception e) {
            System.out.println("Error searching for media: " + e.getMessage());
            return null;
        }
    }

    //try catch
    public String deleteMedia(String name){
        Media media = searchMedia(name);
        String mediaName = media.getName();
        if (media != null ) {
            project.deleteMedia(media);
            System.out.println(mediaName+ " deleted sucessfully");
            return "Media deleted successfully.";
        } else {
            return "Media not found";
        }
    }

    public String deleteAllMedia(){
        if (project.checkEmpty()){
            System.out.println();
            return "Project is already empty";
        } else {
            project.deleteAllMedia();
            return "Media has been deleted";
        }
    }
    //static methods for saving user media to temporary directory in {working directory}/userMedia
    //(to prevent issues if the user modifies the original media we make hard copies of the media imported)

    private static void saveMedia(MultipartFile multipartFile) throws IOException {
        String workingDir = System.getProperty("user.dir");
        System.out.println("working dir: " + workingDir);
        File destFile = new File(workingDir + File.separator+"userMedia"+File.separator + multipartFile.getOriginalFilename());
        multipartFile.transferTo(destFile);

    }
    private static void saveMedia(String filePath){
        Path mediaPath = Paths.get(filePath);
        Path outputPath = Paths.get("userMedia/" + mediaPath.getFileName());
        try{
            Files.copy(mediaPath,outputPath);
        }catch(FileAlreadyExistsException e){
            System.out.println("File already exists");
            System.out.println(e.getMessage());

        }catch(DirectoryNotEmptyException e){
            System.out.println("Directory not empty Exception");
            System.out.println(e.getMessage());

        }catch(SecurityException e){
            System.out.println("File permission exception: ");
            System.out.println(e.toString());
        }catch(Exception e){
            System.out.println("Save media exception: " + e.toString());
        }

    }
}

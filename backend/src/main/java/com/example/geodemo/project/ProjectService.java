package com.example.geodemo.project;

import com.example.geodemo.exceptions.exception.MediaAlreadyExistsException;
import com.example.geodemo.exceptions.exception.MediaNotFoundException;
import com.example.geodemo.media.Media;

import com.example.geodemo.metaDataExtractor.Extractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.*;
import java.util.List;

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
    public Media uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        try (InputStream inputStream = file.getInputStream()) {
            boolean isMulti = true;
            return processMedia(inputStream, file.getOriginalFilename(), file.getSize(), file.getContentType(), isMulti, file);
        } catch (IOException e) {
            System.out.println("error processing file");
            throw new IllegalArgumentException("Error processing file");
        }
    }

    public Media addFile(String filePath) {
        Path mediaPath = Paths.get(filePath);
        if (!Files.exists(mediaPath)|| Files.isDirectory(mediaPath)) {
           throw new IllegalArgumentException("Invalid path");
        }
            try{
                long fileSize = Files.size(mediaPath);
                String contentType = Files.probeContentType(mediaPath);
                InputStream inputStream = Files.newInputStream(mediaPath);
                boolean isMulti = false;
                return processMedia(inputStream, mediaPath.getFileName().toString(), fileSize, contentType, isMulti, filePath);
            }catch(IOException ex){
                throw new IllegalArgumentException("IOexception", ex);
            }



    }

    //helper method to extract data from file and process it
    private Media processMedia(InputStream inputStream, String fileName, long size, String contentType, boolean isMultipart, Object fileSource) throws IOException {

        System.out.println("Received file:");
        System.out.println("Filename: " + fileName);
        System.out.println("Size: " + size + " bytes");
        System.out.println("Content-Type: " + contentType);

        // extract media metadata
        Media media = extractor.extractMetadata(inputStream, fileName);
        if (media != null) {
            if (project.containsMedia(media.getTitle())) {
                System.out.println("Media named " + media.getTitle() + " Already exists");
                throw new MediaAlreadyExistsException(media.getTitle());
            } else {
                project.addMedia(media);

                // this code is to save the media based on whether its a file or multipartFile
                if (isMultipart && fileSource instanceof MultipartFile) {
                    Path userMediaPath = saveMedia((MultipartFile) fileSource);
                    media.setUserMediaPath(userMediaPath.toString());
                    media.setTitle(userMediaPath.getFileName().toString());
                } else if (fileSource instanceof String) {
                    Path userMediaPath = saveMedia((String) fileSource);
                    media.setUserMediaPath(userMediaPath.toString());
                    media.setTitle(userMediaPath.getFileName().toString());
                }
                System.out.println("File processed successfully, added to project");
                return media;
            }
        } else {
            System.out.println("File has no GPS data");
            throw new IllegalArgumentException("File has no GPS data");
        }
    }



    public String renameProject(String name){

        project.setName(name);
        System.out.println("Project name has been successfully named to " + project.getName());
        return "Project name has been successfully named to " + project.getName();
    }


    // ordered by most recently added/modified, returns a list of all media
    public String mediaOrder() {
        StringBuilder mediaList = new StringBuilder();
        mediaList.append("Media List:\n");
        int order = 0;

        for (int i = project.getProjectList().size() - 1; i >= 0; i--) {
            Media media = project.getProjectList().get(i);
            order += 1;
            mediaList.append(order)
                    .append(". Media Name: ")
                    .append(media.getTitle())
                    .append("\n");
        }

        return mediaList.toString();
    }


    //searches the media in a project, returns media object w features of the media

    public Media searchMedia(String id) {

            //Media media = globalLookUp.get(name);
            Media media = project.getMediaByID(id);
            return media;


    }

    //deletes media given an input of an existing media name

    public void deleteMedia(String id){
        Media media = searchMedia(id);
        String mediaName = media.getTitle();
        if (media != null ) {
            project.deleteMedia(media);
            System.out.println(mediaName+ " deleted sucessfully");

        } else {
            System.out.println("medianotfoundexception");
            throw new MediaNotFoundException(id);
        }
    }

    //simply deletes all media in a project file
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
    private static Path generateDestination(String fileName){
        String name = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        if(!extension.isEmpty()){
            extension = "."+extension;
        }
        Path destinationPath = Paths.get("userMedia",fileName );
        int counter=0;

        while(Files.exists(destinationPath)){
            counter++;
            destinationPath = Paths.get("userMedia", name+"_" + counter + extension);
        }
        return destinationPath;
    }

    private static Path saveMedia(MultipartFile multipartFile) throws IOException {
        Path destination = generateDestination(multipartFile.getOriginalFilename());
        multipartFile.transferTo(destination);
        return destination.toAbsolutePath();

    }
    private static Path saveMedia(String filePath){
        Path origin = Paths.get(filePath);
        String fileName = origin.getFileName().toString();
        Path destination = generateDestination(fileName);

       try {
           Files.copy(origin, destination);
       }catch(Exception e){
           System.out.println("saveMedia exception" + e.toString());
       }
        return destination.toAbsolutePath();
    }
    public List<Media> allMedia(){
        return project.getMediaList();
    }

    public void deleteMediaFinal(String id) {
        // Search for the media object
        Media media = searchMedia(id);

        if (media != null) {
            String mediaTitle = media.getTitle();
            Path usermediaPath = Paths.get(media.getUserMediaPath());

            if (Files.exists(usermediaPath)) {
                try{
                    Files.delete(usermediaPath);
                    project.deleteMedia(media);
                    System.out.println("media deleted");
                }catch(Exception e){
                    System.out.println("Exception in deleteMediaFinal");
                }
            } else {
                System.out.println("File in userMedia not found " + usermediaPath.toString());
                throw new MediaNotFoundException(id);
            }
        } else {
            throw new MediaNotFoundException(id);
        }
    }
}

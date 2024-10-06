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
                try{
                    saveMedia(file);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                return "File processed successfully, added to project";
            } else {
                return "No GPS data found in the file";
            }
        } catch (Exception e) {
            return "Error processing the file: " + e.getMessage();
        }

    }

    public String addFile(String filePath){
        Path mediaPath = Paths.get(filePath);



        if(Files.exists(mediaPath) && !Files.isDirectory(mediaPath)){
            try{
                System.out.println("File at : " + filePath);
                System.out.println("Filename: " + mediaPath.getFileName());
                System.out.println("Size: " + Files.size(mediaPath) + " bytes");
                System.out.println("Content-Type: " + Files.probeContentType(mediaPath));

            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }else{
            return "no valid file at: " + filePath;

        }


        try(InputStream mediaStream = Files.newInputStream(mediaPath)){
            Media media = extractor.extractMetadata(mediaStream, mediaPath.getFileName().toString());
            if (media != null) {

                if (project.containsMedia(media.getName())) {
                    return "Media named: " + media.getName() + " is already part of the project";
                } else {

                    project.addMedia(media);





                }

                // if (!globalLookUp.containsKey(media.getName())) {
                //      globalLookUp.put(media.getName(), media);
                // }

                    saveMedia(filePath);

                return "File processed successfully, added to project";
            } else {
                return "No GPS data found in the file";
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "error processing file: " + e.getMessage();
        }
    }


    public String renameProject(String name){
        project.setName(name);
        System.out.println("Project name has been successfully named to" + project.getName());
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

package com.example.geodemo.project;

import com.example.geodemo.exceptions.exception.MediaNotFoundException;
import com.example.geodemo.media.Media;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


/**
 * RestController for Project
 */
@RestController
@RequestMapping("/project")

public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
 
    //add Media from File upload
    //must have file parameter
    @PostMapping(value = "/upload",  consumes = "multipart/form-data" )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Media> addMedia(@RequestParam("file") MultipartFile file) {
        try{
            return new ResponseEntity<>(projectService.uploadFile(file), HttpStatus.CREATED);

        }catch(IllegalArgumentException ex){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage(),ex);
        }
    }

    //Add media from file path
    //must have String filePath parameter
    @PostMapping(value = "/upload", params="filePath")
    @ResponseStatus(HttpStatus.CREATED)
    public Media addMedia(@RequestParam String filePath){
        try {
            return projectService.addFile(filePath);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage(),ex);
        }
    }



    @GetMapping("/search/{name}")
    public Media searchMedia(@PathVariable String name) {
        Media media = projectService.searchMedia(name);
        if(media==null){
            throw new MediaNotFoundException(name);
        }
        return media;
    }

    @PostMapping("/rename")
    public String renameProject(@RequestParam String name) {
        if(name.equals("")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Name cannot be blank");
        }
        return projectService.renameProject(name);
    }

    @DeleteMapping("/delete/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedia(@PathVariable String name) {
        projectService.deleteMedia(name);
    }

    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllMedia() {
        projectService.deleteAllMedia();
    }

    //prints by most recently modified
    @GetMapping("/order")
    public String printMediaList() {
        return projectService.mediaOrder();
    }

    @GetMapping("/allMedia")
    public List<Media> getAllMedia(){
        return projectService.allMedia();
    }
}

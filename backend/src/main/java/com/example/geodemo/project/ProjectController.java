package com.example.geodemo.project;

import com.example.geodemo.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String addMedia(@RequestParam("file") MultipartFile file) {
        return projectService.uploadFile(file);
    }

    //Add media from file path
    //must have String filePath parameter
    @PostMapping(value = "/upload", params = "filePath")
    public String addMedia(@RequestParam String filePath){
        return projectService.addFile(filePath);
    }


    @GetMapping("/search/{name}")
    public Media searchMedia(@PathVariable String name) {
        return projectService.searchMedia(name);
    }

    @PostMapping("/rename")
    public String renameProject(@RequestParam String name) {
        return projectService.renameProject(name);
    }

    @DeleteMapping("/delete/{name}")
    public String deleteMedia(@PathVariable String name) {
        return projectService.deleteMedia(name);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllMedia() {
        return projectService.deleteAllMedia();
    }

    //prints by most recently modified
    @GetMapping("/order")
    public String printMediaList() {
        return projectService.mediaOrder();
    }
}

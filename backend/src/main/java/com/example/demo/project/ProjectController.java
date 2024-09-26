package com.example.geodemo.project;

import com.example.geodemo.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return projectService.uploadFile(file);
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

    //prints by most recently modified
    @GetMapping("/order")
    public void printMediaList() {
        projectService.mediaOrder();
    }
}

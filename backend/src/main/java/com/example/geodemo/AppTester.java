package com.example.geodemo;

import com.example.geodemo.export.ExportController;
import com.example.geodemo.export.ExportFormat;
import com.example.geodemo.media.Media;
import com.example.geodemo.media.MediaController;
import com.example.geodemo.project.ProjectController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Runs code after application start */
@Component
public class AppTester implements CommandLineRunner {

    ProjectController projectController;
    MediaController mediaController;
    ExportController exportController;

    public AppTester(ProjectController projectController, MediaController mediaController, ExportController exportController){
        this.projectController = projectController;
        this.mediaController = mediaController;
        this.exportController = exportController;
    }

    /**
     * Put test code in this method
     */
    @Override
    public void run(String... args) throws Exception {
        //add media to the project
        //absolute path to sample media in project folder/sample-media
        projectController.addMedia("C:\\Users\\Alex\\IdeaProjects\\geomapper\\sample-media\\england-london-bridge.jpg");
        projectController.addMedia("C:\\Users\\Alex\\IdeaProjects\\geomapper\\sample-media\\germany-english-garden.jpg");

        //rename the project
        projectController.renameProject("test project");

        //search for added media
        projectController.searchMedia("england-london-bridge.jpg");

        //delete media
        //projectController.deleteMedia("england-london-bridge.jpg");

        //list media
        projectController.printMediaList();

        //add annotations
        mediaController.addAnnotations("england-london-bridge.jpg", "This is the London Bridge");

        //rename media
        mediaController.renameMedia("england-london-bridge.jpg", "londonBridge.jpg");

        //export kml
        exportController.export(ExportFormat.KML,"C:/Users/Alex/Desktop", "kmltest");

        //export kmz
        exportController.export(ExportFormat.KMZ, "C:/Users/Alex/Desktop", "kmztest");

        //export project
        exportController.export(ExportFormat.PROJECT, "C:/Users/Alex/Desktop", "project-save");



    }
}

package com.example.geodemo.export;

import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

/**
 * REST API for export KMZ-KML/load project/save project
 * calls methods in ExportService
 */
@RestController
@RequestMapping("/")
public class ExportController {

    ExportService exportService;

    @Autowired
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    /**
     *
     * @param format   KMZ/KML/PROJECT
     * @param filePath Export destination
     * @param fileName File name for kml/kmz/project file
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws TransformerException
     */
    @PostMapping("/export")
    public void export(@RequestParam String format, // Accept as String
                       @RequestParam(required = false) String filePath, // Make filePath optional (we will ignore it)
                       @RequestParam String fileName)
            throws ParserConfigurationException, IOException, TransformerException {
    
        // Convert the string to ExportFormat
        ExportFormat exportFormat = ExportFormat.valueOf(format.toUpperCase());
    
        // Dynamically generate file path to the user's Downloads folder
        String homeDirectory = System.getProperty("user.home");
        String downloadsPath = Paths.get(homeDirectory, "Downloads").toString();
    
        System.out.println("Exporting to: " + downloadsPath);
    
        // Call the service with the dynamically generated path
        exportService.export(exportFormat, downloadsPath, fileName);
    }
    
    // Load project state from .gmp file (also works with .kmz)
    @PostMapping("/loadProject")
    public void importProject(@RequestParam String filePath)
            throws IOException, ParserConfigurationException, SAXException {
        exportService.loadProject(filePath);

    }

}

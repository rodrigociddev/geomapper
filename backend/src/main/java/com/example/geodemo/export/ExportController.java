package com.example.geodemo.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * REST API for export KMZ-KML/load project/save project
 * calls methods in ExportService
 */
@RestController
@RequestMapping("/")
public class ExportController {

    ExportService exportService;

    @Autowired
    public ExportController(ExportService exportService){
        this.exportService=exportService;
    }

    /**
     *
     * @param format KMZ/KML/PROJECT
     * @param filePath Export destination
     * @param fileName File name for kml/kmz/project file
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws TransformerException
     */
    @PostMapping("/export")
    public void export(@RequestParam ExportFormat format,
                       @RequestParam String filePath,
                       @RequestParam String fileName)
            throws ParserConfigurationException, IOException, TransformerException {
        System.out.println(format);
        exportService.export(format, filePath, fileName);
    }

    //Load project state from .gmp file (also works with .kmz)
    @PostMapping("/loadProject")
    public void importProject(@RequestParam String filePath) throws IOException, ParserConfigurationException, SAXException {
        exportService.loadProject(filePath);

    }





}

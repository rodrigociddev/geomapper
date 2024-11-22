package com.example.geodemo.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.example.geodemo.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
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
    public ResponseEntity<byte []> export(@RequestParam(required = true) String format, // Ensure format is required
                                 @RequestParam(required = true) String filePath, // Make filePath required
                                 @RequestParam String fileName)
            throws ParserConfigurationException, IOException, TransformerException {

        if (format == null || format.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Format parameter is required");
        }

        // Convert the string to ExportFormat
        ExportFormat exportFormat = ExportFormat.valueOf(format.toUpperCase());
        System.out.println("Exporting format: " + exportFormat.getExporterName());
        Path fullPath = Paths.get(filePath, fileName);
        System.out.println("Exporting to: " + fullPath);

        ByteArrayOutputStream byteArrayOutputStream = exportService.export(exportFormat, filePath, fileName);
        byte[] content = byteArrayOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");

        return ResponseEntity.ok()
                .headers(headers)
                .body(content);

    }

    // Load project state from .gmp file (also works with .kmz)
    @PostMapping("/loadProject")
    public List<Media> importProject(@RequestParam String filePath)
            throws IOException, ParserConfigurationException, SAXException {
        System.out.println("Loading filePath: " + filePath);
        return exportService.loadProject(filePath);
    }
}

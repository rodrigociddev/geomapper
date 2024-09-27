package com.example.geodemo.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    ExportService exportService;

    public ExportController(ExportService exportService){
        this.exportService=exportService;
    }

    @PostMapping("/kml")
    public void exportKml(@RequestParam String filePath,@RequestParam String fileName) throws ParserConfigurationException, TransformerException, IOException {
        exportService.exportKml(filePath, fileName);

    }


    @PostMapping("kmz")
    public void exportKmz(@RequestParam String filePath, @RequestParam String fileName) throws ParserConfigurationException, IOException, TransformerException {
        exportService.exportKmz(filePath, fileName);
    }

}

package com.example.geodemo.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.example.geodemo.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.example.geodemo.export.importer.Importer;

/**
 *
 */
@Service
public class ExportService {

    private Map<String,Exporter> exporters;
    private Importer importer;

    @Autowired
    public ExportService(Map<String,Exporter> exporters, Importer importer){
        this.exporters = exporters;

        this.importer = importer;
    }
    //Calls export() on the exporter type specified by @param type
    public ByteArrayOutputStream export(ExportFormat format) throws ParserConfigurationException, IOException, TransformerException {
        Exporter exporter = exporters.get(format.getExporterName());
        return exporter.export();
    }
    //load .gmp
    public List<Media> loadProject(String filePath) throws IOException, ParserConfigurationException, SAXException {
        return importer.load(filePath);
    }


}

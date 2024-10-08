package com.example.geodemo.export;

import com.example.geodemo.export.importer.Importer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Map;

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
    public void export(ExportFormat format,String filePath, String fileName) throws ParserConfigurationException, IOException, TransformerException {
        Exporter exporter = exporters.get(format.getExporterName());
        exporter.export(filePath,fileName);

    }
    //load .gmp
    public void loadProject(String filePath) throws IOException, ParserConfigurationException, SAXException {
        importer.load(filePath);
    }


}

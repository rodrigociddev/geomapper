package com.example.geodemo.export;

import com.example.geodemo.export.kml.KmlExporter;
import com.example.geodemo.export.kmz.KmzExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ExportService {

    private Map<String,Exporter> exporters;

    @Autowired
    public ExportService(Map<String,Exporter> exporters){
        this.exporters = exporters;
        System.out.println(exporters.size());
    }

    public void export(ExportFormat format,String filePath, String fileName) throws ParserConfigurationException, IOException, TransformerException {
        Exporter exporter = exporters.get(format.getExporterName());
        exporter.export(filePath,fileName);

    }


}

package com.example.geodemo.export;

import com.example.geodemo.export.kml.KmlExporter;
import com.example.geodemo.export.kmz.KmzExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
@Service
public class ExportService {

    KmlExporter kmlExporter;
    KmzExporter kmzExporter;

    @Autowired
    public ExportService(KmlExporter kmlExporter, KmzExporter kmzExporter){
        this.kmlExporter = kmlExporter;
        this.kmzExporter = kmzExporter;
    }

    public void exportKml(String filePath, String fileName) throws ParserConfigurationException, TransformerException, IOException {
        kmlExporter.export(filePath, fileName);

    }
    public void exportKmz(String filePath, String fileName) throws ParserConfigurationException, IOException, TransformerException {
        kmzExporter.export(filePath, fileName);
    }


}

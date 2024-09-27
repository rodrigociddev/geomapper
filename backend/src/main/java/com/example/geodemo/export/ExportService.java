package com.example.geodemo.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.List;
@Service
public class ExportService {

    Exporter exporter;

    @Autowired
    public ExportService(Exporter exporter){
        this.exporter = exporter;
    }

    public void export(String filePath, String fileName) throws ParserConfigurationException, TransformerException {
        exporter.export(filePath, fileName);

    }


}

package com.example.geodemo.export.exporters;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.geodemo.export.Exporter;
import com.example.geodemo.export.builder.KmlBuilder;
import com.example.geodemo.export.builder.KmlDom;

/**
 * Utilizes KmzExporter to save current state of the project to disk with extension .gmp
 */
@Component("ProjectExporter")
public class ProjectExporter implements Exporter {
    private KmlBuilder kmlBuilder;
    private KmzExporter kmzExporter;

    @Autowired
    public ProjectExporter(KmzExporter kmzExporter, KmlBuilder kmlBuilder){
        this.kmlBuilder = kmlBuilder;
        this.kmzExporter = kmzExporter;
    }

    @Override
    public ByteArrayOutputStream export(String filePath, String fileName) throws TransformerException, ParserConfigurationException, IOException {
        KmlDom kmlDom = kmlBuilder.buildKML();
        return kmzExporter.buildArchive(kmlDom.getKmlDoc(), new File(System.getProperty("user.dir")+File.separator+"userMedia"),filePath, fileName);

    }
}

package com.example.geodemo.export.exporters;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Component;

import com.example.geodemo.export.Exporter;
import com.example.geodemo.export.builder.KmlBuilder;
import com.example.geodemo.export.builder.KmlDom;

/**
 * Builds the KML using KmlBuilder and saves it on disk
 */
@Component("KmlExporter")
public class KmlExporter implements Exporter {

    private KmlBuilder kmlBuilder;

    public KmlExporter(KmlBuilder kmlBuilder){
        this.kmlBuilder = kmlBuilder;
    }

    public void export(String filePath, String fileName) throws TransformerException, ParserConfigurationException {


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        KmlDom kmldom = kmlBuilder.buildKML();

        DOMSource source = new DOMSource(kmldom.getKmlDoc());

        StreamResult result = new StreamResult(new File(filePath+"/"+fileName));

        transformer.transform(source,result);


    }
}

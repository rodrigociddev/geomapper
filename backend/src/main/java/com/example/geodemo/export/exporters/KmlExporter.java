package com.example.geodemo.export.exporters;

import com.example.geodemo.export.Exporter;
import com.example.geodemo.export.builder.KmlBuilder;
import com.example.geodemo.export.builder.KmlDom;
import org.springframework.stereotype.Component;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Builds the KML using KmlBuilder and saves it on disk
 */
@Component("KmlExporter")
public class KmlExporter implements Exporter {

    KmlBuilder kmlBuilder;

    public KmlExporter(KmlBuilder kmlBuilder){
        this.kmlBuilder = kmlBuilder;
    }

    public void export(String filePath, String fileName) throws TransformerException, ParserConfigurationException {


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        KmlDom kmldom = kmlBuilder.buildKML();

        DOMSource source = new DOMSource(kmldom.getKmlDoc());

        StreamResult result = new StreamResult(new File(filePath+"/"+fileName+".kml"));

        transformer.transform(source,result);


    }
}

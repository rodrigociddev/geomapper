package com.example.geodemo.export.kmz;

import com.example.geodemo.export.Exporter;
import com.example.geodemo.export.kml.KmlBuilder;
import com.example.geodemo.export.kml.KmlDom;
import com.example.geodemo.export.kml.KmlExporter;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component("KmzExporter")
public class KmzExporter implements Exporter {

    KmlBuilder kmlBuilder;

    @Autowired
    public KmzExporter(KmlBuilder kmlBuilder) {
        this.kmlBuilder = kmlBuilder;
    }

    @Override
    public void export(String filePath, String fileName) throws TransformerException, ParserConfigurationException, IOException {
        KmlDom kmlDom = kmlBuilder.buildKML();
        Document dom = kmlDom.getKmlDoc();


        NodeList placeMarks = dom.getElementsByTagName("Placemark");

        for(int i =0; i<placeMarks.getLength();i++){
            Element placeMark = (Element)placeMarks.item(i);
            String mediaName = placeMark.getElementsByTagName("name").item(0).getTextContent();
            Element descriptionElement = (Element)placeMark.getElementsByTagName("description").item(0);
            String oldDescription = descriptionElement.getTextContent();
            descriptionElement.removeChild(descriptionElement.getFirstChild());
            String newDescription = "<img style=\"max-width:500px;\" src=\"files/"+ mediaName + "\">" +oldDescription;
            descriptionElement.appendChild(dom.createCDATASection(newDescription));
            Element balloonVis = dom.createElement("gx:balloonVisibility");
            balloonVis.appendChild(dom.createTextNode("1"));
            descriptionElement.getParentNode().insertBefore(balloonVis, descriptionElement.getNextSibling());

        }

        //write kml to the zip file
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(filePath+File.separator+fileName+".kmz"));
        zipOut.putNextEntry(new ZipEntry("doc.kml"));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        try (StringWriter writer = new StringWriter()) {
            transformer.transform(new DOMSource(dom), new StreamResult(writer));
            zipOut.write(writer.toString().getBytes());
        }
        zipOut.closeEntry();

        File dir = new File(System.getProperty("user.dir")+File.separator+"userMedia");

        for (File file:dir.listFiles()){
            ZipEntry zipEntry = new ZipEntry("files/"+ file.getName());
            zipOut.putNextEntry(zipEntry);
            Files.copy(file.toPath(), zipOut);
            zipOut.closeEntry();
        }
        zipOut.close();








    }
}

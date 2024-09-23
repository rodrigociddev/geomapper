package com.example.demo.generator;

import com.example.demo.media.Media;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class KmlExporter {
    public static void export(List<Media> mediaList)throws ParserConfigurationException, TransformerException{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("kml");
        rootElement.setAttribute("xmlns","http://www.opengis.net/kml/2.2");
        doc.appendChild(rootElement);

        Element document = doc.createElement("Document");
        rootElement.appendChild(document);

        for(Media media:mediaList){
            Element placemark = doc.createElement("Placemark");
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(media.getName()));
            placemark.appendChild(name);

            Element description = doc.createElement("description");
            description.appendChild(doc.createTextNode(media.getAnnotations()));
            placemark.appendChild(description);

            Element point = doc.createElement("Point");
            Element coordinates = doc.createElement("coordinates");
            coordinates.appendChild(doc.createTextNode(Double.toString(media.getLatitude()) + media.getLongitude() + "0.0"));
            point.appendChild(coordinates);
            placemark.appendChild(point);

            document.appendChild(placemark);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("KMLFile.kml"));

        transformer.transform(source,result);


    }
}

package com.example.geodemo.export.kml;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class KmlDom {

    public Document kmlDoc;
    public Element docElement;


    public KmlDom(String projectName) throws ParserConfigurationException{

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        this.kmlDoc = docBuilder.newDocument();


        Element rootElement = kmlDoc.createElement("kml");
        rootElement.setAttribute("xmlns","http://www.opengis.net/kml/2.2");
        rootElement.setAttribute("xmlns:gx", "http://www.google.com/kml/ext/2.2" );
        kmlDoc.appendChild(rootElement);

        Element document = kmlDoc.createElement("Document");
        docElement = document;

        Element projName = kmlDoc.createElement("name");
        projName.appendChild(kmlDoc.createTextNode(projectName));

        docElement.appendChild(projName);

        rootElement.appendChild(document);


    }

    public void addPlaceMark(String name, String description, Double latitude, Double longitude){

        Element placemark = kmlDoc.createElement("Placemark");

        Element nameElement = kmlDoc.createElement("name");
        nameElement.appendChild(kmlDoc.createTextNode(name));
        placemark.appendChild(nameElement);

        Element descriptionElement = kmlDoc.createElement("description");
        descriptionElement.appendChild(kmlDoc.createTextNode(description));
        placemark.appendChild(descriptionElement);

        Element point = kmlDoc.createElement("Point");
        Element coordinates = kmlDoc.createElement("coordinates");
        coordinates.appendChild(kmlDoc.createTextNode(Double.toString(longitude)+"," + latitude +", " +"0.0"));
        point.appendChild(coordinates);
        placemark.appendChild(point);

        docElement.appendChild(placemark);
    }
    public Document getKmlDoc() {
        return kmlDoc;
    }

    public Element getDocElement() {
        return docElement;
    }


}

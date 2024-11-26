package com.example.geodemo.export.builder;

import com.example.geodemo.media.Media;
import com.example.geodemo.project.Project;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

/**
 * Builds the KML file using media objects from project
 * Utilizes KmlDom
 */
@Component
public class KmlBuilder {

    private Document kmlDom;
    private Project project;


    public KmlBuilder(Project project) {
        this.project = project;

    }
    public KmlDom buildKML() throws ParserConfigurationException {

        List < Media > mediaList = project.getMediaList();

        System.out.println(mediaList.size());
        KmlDom kml = new KmlDom(project.getName());
        for (Media media: mediaList) {

            System.out.println("latitude" + media.getLatitude());
            kml.addPlaceMark(media.getTitle(), media.getAnnotations(), media.getLatitude(), media.getLongitude());
        }
        return kml;


    }
}
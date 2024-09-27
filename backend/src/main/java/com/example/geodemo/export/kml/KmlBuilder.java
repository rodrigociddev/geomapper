package com.example.geodemo.export.kml;


import com.example.geodemo.media.Media;
import com.example.geodemo.project.Project;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;
@Component
public class KmlBuilder {

    Document kmlDom;
    Project project;


    public KmlBuilder(Project project){
        this.project = project;

    }
    public KmlDom buildKML () throws ParserConfigurationException {

        List<Media> mediaList = project.getProjectList();

        System.out.println(mediaList.size());
        KmlDom kml = new KmlDom(project.getName());
        for(Media media:mediaList){

            System.out.println("latitude" + media.getLatitude());
            kml.addPlaceMark(media.getName(), media.getAnnotations(),media.getLatitude(),media.getLongitude());
        }
        return kml;


    }
}

package com.example.geodemo.export.importer;

import com.example.geodemo.media.Media;
import com.example.geodemo.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Component
public class Importer {

    private Project project;

    @Autowired
    public Importer(Project project) {
        this.project = project;
    }

    public void load(String filePath) throws IOException, SAXException, ParserConfigurationException {
        buildMedia(filePath);
        extractMedia(filePath, "userMedia");


    }

    public void extractMedia(String filePath, String destinationPath) throws IOException {
        try(ZipFile zipFile = new ZipFile(filePath)){


            List<ZipEntry> mediaEntries = zipFile.stream().filter(zipEntry -> zipEntry.getName().startsWith("files/")).collect(Collectors.toList());

            for(ZipEntry zipEntry: mediaEntries){

                InputStream zipEntryStream = zipFile.getInputStream(zipEntry);
                String fileEntryName = zipEntry.getName();
                Path destination = Paths.get(destinationPath+"/"+ fileEntryName.substring(fileEntryName.lastIndexOf("/")+1));
                Files.copy(zipEntryStream,destination);

            }


        }catch(Exception e) {
            System.out.println("extractMedia Exception: "+  e.getMessage());
        }


    }
    public void buildMedia(String filePath) throws ParserConfigurationException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();


        try(ZipFile zipFile = new ZipFile(filePath)){
            ZipEntry kmlEntry =  zipFile.getEntry("doc.kml");

            if(kmlEntry == null){
                System.out.println("no doc.kml");
                return;
            }

            InputStream kmlInputStream = zipFile.getInputStream(kmlEntry);
            Document kmlDoc = documentBuilder.parse(kmlInputStream);

            System.out.println("namespace uri: "+ kmlDoc.getDocumentElement().getNamespaceURI());
            System.out.println(kmlDoc.getDocumentElement().getTextContent());

            String kmlNamespaceUri = "http://www.opengis.net/kml/2.2";
            NodeList placeMarks = kmlDoc.getDocumentElement().getElementsByTagNameNS(kmlNamespaceUri, "Placemark");
            System.out.println("Placemarks: " + placeMarks.getLength());

            for(int i =0;i< placeMarks.getLength();i++){

                Element placeMark = (Element)placeMarks.item(i);

                String name = placeMark.getElementsByTagNameNS(kmlNamespaceUri, "name").item(0).getTextContent();

                String description = placeMark.getElementsByTagNameNS(kmlNamespaceUri, "description").item(0).getTextContent();


                String coordinates = placeMark.getElementsByTagNameNS(kmlNamespaceUri, "coordinates").item(0).getTextContent();
                String longitude = coordinates.split(",")[0];
                String latitude = coordinates.split(",")[1];

                System.out.println("adding media: " + name);
                project.addMedia(new Media(Double.parseDouble(longitude), Double.parseDouble(latitude),name,description));



            }


        }catch(Exception e){
            System.out.println("buildMedia exception: "+ e.getMessage());
        }

    }


}






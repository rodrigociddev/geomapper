package com.example.geodemo.export.exporters;

import com.example.geodemo.export.Exporter;
import com.example.geodemo.export.builder.KmlBuilder;
import com.example.geodemo.export.builder.KmlDom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Utilizes KmlBuilder to generate doc.kml, adds the image to the description(to be changed)
 * and packages it together with the media files in /files
 */

@Component("KmzExporter")
public class  KmzExporter implements Exporter {

    private KmlBuilder kmlBuilder;

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
       buildArchive(dom,new File(System.getProperty("user.dir")+File.separator+"userMedia"), filePath, fileName+".kmz");

    }

    /**
     *
     * @param dom KML document object model
     * @param mediaDir directory where our media is stored on disk
     * @param filePath export path
     * @param fileName KMZ filename
     * @throws IOException
     * @throws TransformerConfigurationException
     */
    protected void buildArchive(Document dom, File mediaDir, String filePath, String fileName) throws IOException, TransformerConfigurationException {
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(filePath+File.separator+fileName));
        zipOut.putNextEntry(new ZipEntry("doc.kml"));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        try (StringWriter writer = new StringWriter()) {
            transformer.transform(new DOMSource(dom), new StreamResult(writer));
            zipOut.write(writer.toString().getBytes());
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        zipOut.closeEntry();


        File[] files = mediaDir.listFiles();
        if(files == null){
            System.out.println("no media files");
        }else{
            for (File file:files){
                ZipEntry zipEntry = new ZipEntry("files/"+ file.getName());
                zipOut.putNextEntry(zipEntry);
                Files.copy(file.toPath(), zipOut);
                zipOut.closeEntry();
            }
        }

        zipOut.close();
    }
}

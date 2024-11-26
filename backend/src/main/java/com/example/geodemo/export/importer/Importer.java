package com.example.geodemo.export.importer;

import com.example.geodemo.media.Media;
import com.example.geodemo.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Load .gmp file into application
 * generates Media objects from doc.kml
 * copies user media into /userMedia
 */
@Component
public class Importer {

    private Project project;

    @Autowired
    public Importer(Project project) {
        this.project = project;
    }

    public List<Media> load(String filePath) throws IOException, ParserConfigurationException {
        project.reset();
        buildMedia(filePath);
        extractMedia(filePath, "userMedia");
        System.out.println(project.getMediaList().size());
        return project.getMediaList();

    }

    private void extractMedia(String filePath, String destinationPath){
        try(ZipFile zipFile = new ZipFile(filePath)){


            List<ZipEntry> mediaEntries = zipFile.stream().filter(zipEntry -> zipEntry.getName().startsWith("userMedia/")).collect(Collectors.toList());

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
    private void buildMedia(String filePath){


        try(ZipFile zipFile = new ZipFile(filePath)){
            ZipEntry mediaListEntry =  zipFile.getEntry("mediaList");

            if(mediaListEntry == null){
                System.out.println("no mediaList");
                return;
            }

            ObjectInputStream inputStream = new ObjectInputStream(zipFile.getInputStream(mediaListEntry));
            List<Media> mediaList = (List<Media>)inputStream.readObject();
            mediaList.forEach((media) -> {
                media.setUUID(UUID.randomUUID().toString());
                project.addMedia(media);
            });





        }catch(Exception e){
            System.out.println("buildMedia exception: "+ e.getMessage());
        }

    }


}






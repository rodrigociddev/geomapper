package com.example.geodemo.export.exporters;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.example.geodemo.media.Media;
import com.example.geodemo.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.geodemo.export.Exporter;
import com.example.geodemo.export.builder.KmlBuilder;
import com.example.geodemo.export.builder.KmlDom;

/**
 * Utilizes KmzExporter to save current state of the project to disk with extension .gmp
 */
@Component("ProjectExporter")
public class ProjectExporter implements Exporter {
        Project project;

    @Autowired
    public ProjectExporter(Project project){
        this.project=project;
    }

    @Override
    public ByteArrayOutputStream export() throws TransformerException, ParserConfigurationException, IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        //write mediaList into zip
        List<Media> mediaList =project.getMediaList();

        ZipEntry mediaListEntry = new ZipEntry("mediaList");
        zipOutputStream.putNextEntry(mediaListEntry);
        ObjectOutputStream oos = new ObjectOutputStream(zipOutputStream);

        oos.writeObject(mediaList);
        zipOutputStream.closeEntry();

        //write userMedia into zip
        Path userMediaPath = Paths.get("userMedia");
        Files.walkFileTree(userMediaPath,new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relativePath = userMediaPath.relativize(file);
                zipOutputStream.putNextEntry(new ZipEntry("userMedia/" +relativePath.toString()));
                Files.copy(file,zipOutputStream);
                zipOutputStream.closeEntry();
                return FileVisitResult.CONTINUE;
            }
        });

        zipOutputStream.close();
        oos.close();
        return byteArrayOutputStream;
    }
}

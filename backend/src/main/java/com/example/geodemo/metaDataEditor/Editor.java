package com.example.geodemo.metaDataEditor;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.geodemo.exceptions.exception.MediaNotFoundException;
import com.example.geodemo.media.Media;
import com.example.geodemo.project.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;

public class Editor {
    private final ProjectService projectService;

    @Autowired
    public Editor(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void updateGPSMetadata(String id, double latitude, double longitude) {
        Media media = projectService.searchMedia(id);
        if (media == null) {
            throw new MediaNotFoundException(id);
        }

        File imageFile = new File(media.getUserMediaPath());
        if (!imageFile.exists()) {
            throw new IllegalArgumentException("Media file does not exist: " + media.getUserMediaPath());
        }

        try (FileOutputStream fos = new FileOutputStream(imageFile);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            TiffOutputSet outputSet = null;

            ImageMetadata metadata = Imaging.getMetadata(imageFile);
            if (metadata instanceof JpegImageMetadata) {
                JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                TiffImageMetadata exif = jpegMetadata.getExif();

                if (exif != null) {
                    outputSet = exif.getOutputSet();
                }
            }

            if (outputSet == null) {
                outputSet = new TiffOutputSet();
            }

            outputSet.setGPSInDegrees(longitude, latitude);

            new ExifRewriter().updateExifMetadataLossless(imageFile, bos, outputSet);

        } catch (IOException | ImagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update GPS metadata for image " + id, e);
        }
    }
}

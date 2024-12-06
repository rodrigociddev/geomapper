package com.example.geodemo.metaDataExtractor;

import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.example.geodemo.media.Media;

import java.io.InputStream;
import java.util.UUID;

/**
 * Contains static methods for extracting metadata from media files using
 * MetaDataExtractor API
 * https://github.com/drewnoakes/metadata-extractor
 */
public class Extractor {

    public static Media extractMetadata(InputStream inputStream, String fileName) {
        Media media = new Media();
        media.setTitle(fileName); // Always set the filename as title
        media.setUUID(UUID.randomUUID().toString()); // Generate UUID for the media

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

            if (gpsDirectory != null) {
                GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                if (geoLocation != null) {
                    media.setLatitude(geoLocation.getLatitude());
                    media.setLongitude(geoLocation.getLongitude());
                } else {
                    System.out.println("No GPS data found for file: " + fileName);
                    media.setLatitude(0.0); // Default latitude
                    media.setLongitude(0.0); // Default longitude
                }
            } else {
                System.out.println("No GPS directory found for file: " + fileName);
                media.setLatitude(0.0); // default latitude
                media.setLongitude(0.0); // Default longitude
            }
        } catch (Exception e) {
            System.err.println("Error reading metadata for file: " + fileName);
            e.printStackTrace();
            media.setLatitude(0.0); // Default latitude
            media.setLongitude(0.0); // Default longitude
        }

        return media; // Return media even if GPS data is missing
    }
}

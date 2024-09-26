ackage com.example.geodemo.metaDataExtraction;

import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.example.geodemo.media.Media;

import java.io.InputStream;

public class Extractor {
    //contians extractor, uses library to extract information from media
    public static Media extractMetadata(InputStream inputStream, String fileName) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

            Media media = new Media();
            media.setName(fileName);

            if (gpsDirectory != null) {
                GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                if (geoLocation != null) {
                    media.setLatitude(geoLocation.getLatitude());
                    media.setLongitude(geoLocation.getLongitude());

                    if ((media.getLatitude() == 0.0) && (media.getLongitude() == 0.0)) {
                        return null;
                    }

                } else {
                    return null; // no GPS data found
                }
            } else {
                return null; // no GPS directory found
            }

            return media;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

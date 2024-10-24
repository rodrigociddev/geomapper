package com.example.geodemo.media;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents Media added by the user
 */
public class Media {
    private String filePath;

    @NotNull
    private String name;

    private String annotations;
    private double longitude;
    private double latitude;




    public Media(double latitude, double longitude, String name, String annotation){
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.annotations = annotation;
    }

    public Media(double latitude, double longitude, String name) {

        this(latitude,longitude,name,"");
    }

    public Media() {

    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    @Override
    public String toString() {
        return "Media{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", annotations=" + annotations +
                ", name='" + name + '\'' +
                '}';
    }
}

package com.example.geodemo.media;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * Represents Media added by the user
 */
public class Media implements Serializable {

    //absolute path to file in userMedia
    private String userMediaPath;
    private String uuid;
    @NotNull
    private String title;

    private String annotations;
    private double longitude;
    private double latitude;




    public Media(double latitude, double longitude, String title, String annotation){
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.annotations = annotation;
    }

    public Media(double latitude, double longitude, String title) {

        this(latitude,longitude, title,"");
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public String getUUID(){
        return this.uuid;
    }
    public void setUUID(String id){
        this.uuid =id;
    }
    public void setUserMediaPath(String userMediaPath){
        this.userMediaPath = userMediaPath;
    }
    public String getUserMediaPath(){
        return this.userMediaPath;
    }


    @Override
    public String toString() {
        return "Media{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", annotations=" + annotations +
                ", name='" + title + '\'' +
                '}';
    }
}

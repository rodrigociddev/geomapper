package com.example.geodemo.project;


import com.example.geodemo.media.Media;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Repository
public class Project {
    private String name = "GeoMapper demo";
    private List<Media> mediaList;
    static public HashMap<String, Media> mediaLookUp;

    public Project() {
        mediaList = new ArrayList<>();
        mediaLookUp = new HashMap<>();

        //create userMedia directory
        String workingDir = System.getProperty("user.dir") + File.separator + "userMedia";
        File dir = new File(workingDir);
        dir.mkdirs();

    }

    // adding media to both the list(maintains order) and lookup
    public void addMedia(Media media) {
        mediaList.add(media);
        mediaLookUp.put(media.getName(), media);
    }

    public void deleteMedia(Media media) {
        mediaList.remove(media);
        mediaLookUp.remove(media.getName(), media);
    }

    //returns all media
    public List<Media> getProjectList() {
        return mediaList;
    }

    //looking up by name through hashmap
    public Media getMediaByName(String name) {
        return mediaLookUp.get(name);
    }

    //just to check
    public boolean containsMedia(String name) {
        return mediaLookUp.containsKey(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

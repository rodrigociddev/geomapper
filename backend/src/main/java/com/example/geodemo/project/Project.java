package com.example.geodemo.project;


import com.drew.tools.FileUtil;
import com.example.geodemo.media.Media;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Class that represents an instance of the project
 *
 */
@Repository
public class Project {
    private String name = "GeoMapper demo";

    //Collection of media imported by the user
    private List<Media> mediaList;

    //hashMap of the same media objects for fast lookup
    static private HashMap<String, Media> mediaLookUp;

    public Project() {
        reset();
    }
    public void reset(){
        mediaList = new ArrayList<>();
        mediaLookUp = new HashMap<>();

        //create userMedia directory

        //wipe if exists
        File userMediaDir = new File("userMedia");
        if(userMediaDir.exists()){
            try{
                FileUtils.deleteDirectory(userMediaDir);
            }catch(IOException e){
                System.out.println("failed to delete existing userMedia");
                System.out.println(e.toString());
                System.exit(1);
            }
        }userMediaDir.mkdirs();
    }

    // adding media to both the list(maintains order) and lookup
    public void addMedia(Media media) {
        mediaList.add(media);
        mediaLookUp.put(media.getUUID(), media);
    }

    public void deleteMedia(Media media) {
        mediaList.remove(media);
        mediaLookUp.remove(media.getUUID(), media);
    }

    public void deleteAllMedia(){
        mediaList.clear();
        mediaLookUp.clear();

    }

    public boolean checkEmpty(){
        if (mediaLookUp.isEmpty() && mediaList.isEmpty()){
            return true;
        } else {
            return false;}
    }

    //looking up by name through hashmap
    public Media getMediaByID(String id) {
        return mediaLookUp.get(id);
    }

    //just to check
    public boolean containsMedia(String id) {
        return mediaLookUp.containsKey(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<Media> getMediaList(){
        return mediaList;
    }
}

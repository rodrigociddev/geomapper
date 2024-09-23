package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Project {
    private List<Media> mediaList;
    private HashMap<String, Media> mediaLookUp;

    public Project() {
        mediaList = new ArrayList<>();
        mediaLookUp = new HashMap<>();
    }

    // adding media to both the list(maintians order) and lookup
    public void addMedia(Media media) {
        mediaList.add(media);
        mediaLookUp.put(media.getName(), media);
    }

    public void deleteMedia(Media media) {
        mediaList.remove(media);
        mediaLookUp.remove(media.getName(), media);
    }

    //returns all media
    public List<Media> getMediaList() {
        return mediaList;
    }

    //looking up by name thru hashmap
    public Media getMediaByName(String name) {
        return mediaLookUp.get(name);
    }

    //just to check
    public boolean containsMedia(String name) {
        return mediaLookUp.containsKey(name);
    }

}

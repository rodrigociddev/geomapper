package com.example.demo.media;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MediaService {

    private List<Media> mediaList = new ArrayList<Media>();
    private HashMap<String, Media> mediaLookUp = new HashMap<>();

    public MediaService(){}


}

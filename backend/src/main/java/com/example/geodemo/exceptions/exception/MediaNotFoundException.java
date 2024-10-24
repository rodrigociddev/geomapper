package com.example.geodemo.exceptions.exception;

import com.example.geodemo.media.MediaService;

public class MediaNotFoundException extends RuntimeException{

    public MediaNotFoundException(){
        super("Media not found");
    }
    public MediaNotFoundException(String message){
        super("media named " + message + " not found");
    }
}

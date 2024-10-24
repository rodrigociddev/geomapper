package com.example.geodemo.exceptions.exception;

import com.example.geodemo.media.Media;

public class MediaAlreadyExistsException extends RuntimeException{
    public MediaAlreadyExistsException(){
        super("Media already exists");
    }
    public MediaAlreadyExistsException(String message){
        super("Media with name "+ message+ " already exists");
    }

}

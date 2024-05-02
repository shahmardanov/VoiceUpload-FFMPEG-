package com.example.fileupload.exception.handler;

public class VoiceNotFoundException extends RuntimeException {

    public VoiceNotFoundException(String message, String code) {
        super(message);
    }

}

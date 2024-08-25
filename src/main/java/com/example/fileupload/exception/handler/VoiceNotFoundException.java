package com.example.fileupload.exception.handler;

import lombok.Getter;

@Getter
public class VoiceNotFoundException extends RuntimeException {


    public VoiceNotFoundException(String message) {
        super(message);
    }

}

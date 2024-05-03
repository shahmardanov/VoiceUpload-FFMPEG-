package com.example.fileupload.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${upload.directory}")
    private String uploadDirectory;

    public String getUploadDirectory() {
        return uploadDirectory;
    }
}
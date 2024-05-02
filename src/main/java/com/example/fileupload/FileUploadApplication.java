package com.example.fileupload;

import com.example.fileupload.direction.DirectoryCreator;
import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileUploadApplication.class, args);
        String directoryPath ="C:\\JavaFiles";
        DirectoryCreator.createDirectory(directoryPath);
    }

}

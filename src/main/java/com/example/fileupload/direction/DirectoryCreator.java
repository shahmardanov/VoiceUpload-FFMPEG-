package com.example.fileupload.direction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class DirectoryCreator  {

    public static void createDirectory(String directoryPath) {
        try {
            Path path = Paths.get(directoryPath);
            Files.createDirectories(path);
            System.out.println("Directory created successfully!");
        } catch (IOException e) {
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }
}

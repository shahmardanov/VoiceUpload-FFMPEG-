package com.example.fileupload.controller;

import com.example.fileupload.model.AppConfig;
import com.example.fileupload.model.FileData;
import com.example.fileupload.repository.FileRepository;
import com.example.fileupload.service.FileService;
import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;


    @PostMapping("/upload")
    public String uploadAndConvertToAAC(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload.";
        }

        try {
            // Save the uploaded file to a directory on the server
            String uploadDir = "C:\\JavaFiles"; // Change this to your desired directory
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString();
            String filePath = uploadDir + File.separator + fileName;
            File dest = new File(filePath);
            file.transferTo(dest);

            // Define output file path for AAC conversion
            String outputFilePath = uploadDir + File.separator + fileName + ".aac";

            // Convert the uploaded audio file to AAC format
            fileService.convertToAAC(filePath, outputFilePath);

            return "Audio file uploaded and converted to AAC format successfully!";
        } catch (IOException e) {
            return "Error occurred during file upload or audio conversion: " + e.getMessage();
        }
    }
}


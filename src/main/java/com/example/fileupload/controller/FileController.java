package com.example.fileupload.controller;

import com.example.fileupload.repository.FileRepository;
import com.example.fileupload.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private static final String UPLOAD_DIR ="C:\\\\JavaFiles" ;
    private final FileService fileService;
    private final FileRepository fileRepository;

    @Value("${C:\\JavaFiles}")
    private String userHome;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

        try {
            // Create upload directory if it doesn't exist
            createUploadDirectoryIfNotExists();

            String fileName = UUID.randomUUID().toString() + ".wav";
            String filePath = userHome + File.separator + UPLOAD_DIR + File.separator + fileName;
            File dest = new File(filePath);
            file.transferTo(dest);

            convertToAac(filePath);

            return ResponseEntity.ok().body("File uploaded and converted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }

    private void createUploadDirectoryIfNotExists() throws IOException {
        Path path = Paths.get(userHome, UPLOAD_DIR);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    private <FFmpeg> void convertToAac(String inputFilePath) {
        try {
            FFmpeg ffmpeg = new FFmpeg();
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);

            String outputFilePath = inputFilePath.replace(".wav", ".aac");

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(inputFilePath)
                    .addOutput(outputFilePath)
                    .setAudioBitRate(64_000)
                    .done();

            executor.createJob(builder).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



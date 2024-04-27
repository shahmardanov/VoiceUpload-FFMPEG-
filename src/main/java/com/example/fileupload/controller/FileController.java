package com.example.fileupload.controller;

import com.example.fileupload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/uploadVoiceMessage")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> uploadVoiceMessage(@RequestParam("file") MultipartFile file) {
        try {
            fileService.saveVoiceMessage(file);
            return ResponseEntity.status(HttpStatus.OK).body("Voice message uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload voice message");
        }
    }
}

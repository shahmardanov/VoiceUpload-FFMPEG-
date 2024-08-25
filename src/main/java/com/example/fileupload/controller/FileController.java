package com.example.fileupload.controller;

import com.example.fileupload.exception.handler.VoiceNotFoundException;
import com.example.fileupload.service.FileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/uploads")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/audio")
    public ResponseEntity<String> uploadAndConvertToAAC(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is missing.");
        }

        try {
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                return ResponseEntity.badRequest().body("Fayl adı tapılmadı.");
            }

            String filePath = "C:/JavaFiles/" + fileName;
            file.transferTo(new File(filePath));

            fileService.convertToAAC(filePath);

            return ResponseEntity.ok("Audio fayl yükləndi və uğurla AAC formatına çevrildi!");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Faylın yüklənməsi və ya audio çevrilməsi zamanı xəta baş verdi.");
        }
    }

    @GetMapping("/audio/audioId/{id}")
    public ResponseEntity<Resource> getFileById(@PathVariable Long id) {
        File file = fileService.getFileById(id);
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/audio/audioName/{fileName}")
    public ResponseEntity<Resource> getFileByName(@PathVariable String fileName) {
        File file = fileService.getFileByName(fileName);
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


    @DeleteMapping("/audio/audioId/{id}")
    public ResponseEntity<String> deleteFileById(@PathVariable Long id) {
        try {
            fileService.deleteFileById(id);
            return ResponseEntity.ok("Fayl uğurla silindi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fayl silinərkən xəta baş verdi:");
        }
    }

    @DeleteMapping("/audio/audioName/{fileName}")
    public ResponseEntity<String> deleteFileByName(@PathVariable String fileName) {
        try {
            fileService.deleteFileByName(fileName);
            return ResponseEntity.ok("Fayl uğurla silindi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fayl silinərkən xəta baş verdi.");
        }
    }

}


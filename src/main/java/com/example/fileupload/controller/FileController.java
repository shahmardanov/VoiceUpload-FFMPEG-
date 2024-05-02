package com.example.fileupload.controller;

import com.example.fileupload.model.FileData;
import com.example.fileupload.repository.FileRepository;
import com.example.fileupload.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileRepository fileRepository;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Geçici bir dosya yolu oluştur
            String tempFilePath = "C:\\JavaFiles\\" + file.getOriginalFilename();

            // MultipartFile'den dosyayı geçici bir yere kaydet
            File tempFile = new File(tempFilePath);
            file.transferTo(tempFile);

            // Dosyayı sıkıştır ve sıkıştırılmış dosyanın yolunu al
            String compressedFilePath = fileService.compressFile(tempFilePath);

            // Geçici dosyayı sil
            tempFile.delete();

            return ResponseEntity.ok("Sıkıştırılmış dosyanın URL'si: " + compressedFilePath);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dosya yüklenirken bir hata oluştu.");
        }

        }
    }


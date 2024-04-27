package com.example.fileupload.service;

import com.example.fileupload.model.FileData;
import com.example.fileupload.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {

    @Autowired
    private FileRepository repository;

    public void saveVoiceMessage(MultipartFile file) throws IOException {
        FileData fileData = new FileData();
        fileData.setFileName(file.getOriginalFilename());
        fileData.setFileType(file.getContentType());
        fileData.setData(file.getBytes());
        repository.save(fileData);
    }
}

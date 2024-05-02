package com.example.fileupload.service;

import com.example.fileupload.model.FileData;
import com.example.fileupload.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public String compressFile(String sourceFilePath) throws IOException, SAXException {
        File sourceFile = new File(sourceFilePath);

        String compressedFileName = sourceFile.getName() + ".zip";
        String compressedFilePath = "C:\\JavaFiles\\" + compressedFileName;

        compressFile(sourceFilePath, compressedFilePath);

        // FileData obyekti yaradıp bazaya yazmaq
        FileData compressedFile = new FileData();
        compressedFile.setFileName(sourceFile.getName());
        compressedFile.setCompressedFileUrl(compressedFilePath);

        // Faylın tipini təyin etmək və verilənlər bazasına yazmaq
        String fileType = detectFileType(sourceFile);
        compressedFile.setFileType(fileType);

        // Verilənlər bazasına yaddaşa yazmaq
        fileRepository.save(compressedFile);

        return compressedFilePath;
    }

    public void compressFile(String sourceFilePath, String compressedFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(compressedFilePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ZipOutputStream zos = new ZipOutputStream(bos)) {
            ZipEntry zipEntry = new ZipEntry(getFileName(sourceFilePath));
            zos.putNextEntry(zipEntry);

            try (InputStream inputStream = new FileInputStream(sourceFilePath)) {
                byte[] bytes = new byte[1024];
                int length;
                while ((length = inputStream.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }
            }

            zos.closeEntry();
        }
    }

    private String getFileName(String filePath) {
        return new File(filePath).getName();
    }

    private String detectFileType(File file) throws IOException, SAXException {
        try (InputStream inputStream = new FileInputStream(file)) {
            Parser parser = new AutoDetectParser();
            Metadata metadata = new Metadata();
            parser.parse(inputStream, new org.apache.tika.sax.BodyContentHandler(), metadata, new ParseContext());
            return metadata.get(Metadata.CONTENT_TYPE);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        }
    }
    }




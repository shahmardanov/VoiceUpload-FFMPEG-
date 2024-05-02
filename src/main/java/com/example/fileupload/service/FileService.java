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

    public void convertWavToAac(String inputFilePath, String outputFilePath, int bitrate) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        // Adjust the path to ffmpeg executable accordingly
        processBuilder.command("ffmpeg", "-i", inputFilePath, "-b:a", bitrate + "k", outputFilePath);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        // Wait for the conversion to finish
        process.waitFor();
    }

}




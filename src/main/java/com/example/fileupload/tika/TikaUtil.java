package com.example.fileupload.tika;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.StringWriter;

public class TikaUtil {

    private static final Tika tika = new Tika();

    public static String detectFileType(MultipartFile file) throws IOException {
        return tika.detect(file.getInputStream());
    }

    public static String extractMetadata(MultipartFile file) throws IOException {
        Metadata metadata = new Metadata();
        StringWriter text = new StringWriter();
        tika.parse(file.getInputStream());
        return text.toString();
    }
}


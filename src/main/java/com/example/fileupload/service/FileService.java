package com.example.fileupload.service;

import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class FileService {


    public void convertToAAC(String inputFilePath, String outputFilePath) throws IOException {
        // Create FFmpeg instance
        FFmpeg ffmpeg = new FFmpeg();

        // Define FFmpeg command for audio conversion to AAC format
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFilePath)
                .overrideOutputFiles(true)
                .addOutput(outputFilePath)
                .setAudioCodec("aac")
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // For AAC codec
                .done();

        // Run FFmpeg command
        try {
            ffmpeg.run(builder);

            // Delete the original file after successful conversion
            File originalFile = new File(inputFilePath);
            if (originalFile.exists()) {
                originalFile.delete();
            }
        } catch (IOException e) {
            File originalFile = new File(inputFilePath);
            originalFile.delete();
            throw new IOException("Error occurred during audio conversion: " + e.getMessage());
        }
        }
    }






package com.example.fileupload.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
public class AppConfig {

    @Value("${ffmpeg.path}")
    private String ffmpegPath;

    @Bean
    public String testFfmpeg() {
        try {
            File ffmpegFile = new File(ffmpegPath);
            if (ffmpegFile.exists() && ffmpegFile.canExecute()) {
                System.out.println("FFmpeg Path: " + ffmpegPath);
                ProcessBuilder processBuilder = new ProcessBuilder(ffmpegPath, "-version");
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                process.waitFor();
                return "FFmpeg is configured correctly.";
            } else {
                System.out.println("FFmpeg file not found or not executable at: " + ffmpegPath);
                return "FFmpeg not found or not executable.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error occurred while testing FFmpeg.";
        }
    }
}


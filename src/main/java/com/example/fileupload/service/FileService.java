package com.example.fileupload.service;

import com.example.fileupload.exception.handler.VoiceNotFoundException;
import com.example.fileupload.model.FileData;
import com.example.fileupload.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Value("${ffmpeg.path}")
    private String ffmpegPath;

    @Value("${file.upload.dir}")
    private String uploadDir;

    public void convertToAAC(String inputFilePath) throws IOException {
        String uniqueFileName = generateUniqueFileName();
        String outputFilePath = Paths.get(uploadDir, uniqueFileName + ".aac").toString();

        FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFilePath)
                .overrideOutputFiles(true)
                .addOutput(outputFilePath)
                .setAudioCodec("aac")
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();

        ffmpeg.run(builder);

        File originalFile = new File(inputFilePath);
        if (originalFile.exists()) {
            originalFile.delete();
        }

        String userId = String.valueOf(generateUserId());
        saveToFileDatabase(uniqueFileName + ".aac", userId);
    }

    private String generateUniqueFileName() {
        return UUID.randomUUID().toString();
    }

    private Long generateUserId() {
        return new Random().nextLong();
    }

    private void saveToFileDatabase(String fileName, String userId) {
        FileData audioFile = FileData.builder()
                .fileName(fileName)
                .userId(userId)
                .build();
        fileRepository.save(audioFile);
    }

    public File getFileById(Long id) throws VoiceNotFoundException {
        FileData fileData = fileRepository.findById(id)
                .orElseThrow(() -> new VoiceNotFoundException("Fayl tapılmadı"));

        String filePath = Paths.get(uploadDir, fileData.getFileName()).toString();

        return new File(filePath);
    }

    public File getFileByName(String fileName) throws VoiceNotFoundException {
        FileData fileData = fileRepository.findByFileName(fileName)
                .orElseThrow(() -> new VoiceNotFoundException("Bu adda bir fayl tapılmadı"));

        String filePath = Paths.get(uploadDir, fileData.getFileName()).toString();

        return new File(filePath);
    }

    public void deleteFileById(Long id) {
        FileData fileData = fileRepository.findById(id)
                .orElseThrow(() -> new VoiceNotFoundException("Fayl tapılmadı"));
        deletePhysicalFile(fileData.getFileName());
        fileRepository.deleteById(id);
    }

    public void deleteFileByName(String fileName) {
        FileData fileData = fileRepository.findByFileName(fileName)
                .orElseThrow(() -> new VoiceNotFoundException("Fayl tapılmadı"));
        deletePhysicalFile(fileData.getFileName());
        fileRepository.delete(fileData);

    }

    private void deletePhysicalFile(String fileName) {
        String filePath = Paths.get(uploadDir, fileName).toString();
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}





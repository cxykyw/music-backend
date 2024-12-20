package com.music.util;

import com.music.config.FileUploadConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUtil {
    
    private final FileUploadConfig fileUploadConfig;
    
    public String uploadFile(MultipartFile file, String directory) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + extension;
        
        Path uploadPath = Paths.get(fileUploadConfig.getPath(), directory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);
        
        return directory + "/" + filename;
    }
    
    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(fileUploadConfig.getPath(), filePath);
        Files.deleteIfExists(path);
    }
} 
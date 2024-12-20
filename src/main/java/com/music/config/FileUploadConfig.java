package com.music.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadConfig {
    private String path;
    private long maxSize;
    private String allowedTypes;

    // Getters and Setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public String getAllowedTypes() {
        return allowedTypes;
    }

    public void setAllowedTypes(String allowedTypes) {
        this.allowedTypes = allowedTypes;
    }
} 
package com.careerstreet.file_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile file) throws IOException;

    byte[] downloadFile(Long id);
}

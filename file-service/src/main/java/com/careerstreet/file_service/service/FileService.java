package com.careerstreet.file_service.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface FileService {
//    String uploadFile(MultipartFile file) throws IOException;
    Map uploadFile(MultipartFile file) throws IOException;

//    byte[] downloadFile(Long id);
    void downloadFile(String publicId, HttpServletResponse response) throws IOException;
}

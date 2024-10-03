package com.careerstreet.file_service.controller;

import com.careerstreet.file_service.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/file/")
public class FileController {
    private final FileService fileService;

    @PostMapping("upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(Map.of("error", "File is empty"), HttpStatus.BAD_REQUEST);
        }

        // Kiểm tra định dạng file
        String contentType = file.getContentType();
        if (!contentType.equals("application/pdf") && !contentType.startsWith("image/")) {
            return new ResponseEntity<>(Map.of("error", "Unsupported file type. Please upload a PDF or an image."), HttpStatus.BAD_REQUEST);
        }

        try {
            Map<String, Object> data = this.fileService.uploadFile(file);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(Map.of("error", "File upload failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{publicId}")
    public void downloadFile(@PathVariable String publicId, HttpServletResponse response) throws IOException {
        System.out.println("123");
        fileService.downloadFile(publicId, response);
    }
}

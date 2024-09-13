package com.careerstreet.file_service.controller;

import com.careerstreet.file_service.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/file/")
public class FileController {

    private final FileService fileService;

    @PostMapping("upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        String uploadFile = fileService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadFile);
    }

    @GetMapping("download/{id}")
    public ResponseEntity<?> download(@PathVariable Long id){
        byte[] fileData = fileService.downloadFile(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("file/jpg"))
                .body(fileData);
    }
}

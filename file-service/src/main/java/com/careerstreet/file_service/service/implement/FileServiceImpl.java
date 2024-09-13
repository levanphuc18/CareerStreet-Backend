package com.careerstreet.file_service.service.implement;

import com.careerstreet.file_service.entity.FileData;
import com.careerstreet.file_service.repositoty.FileRepository;
import com.careerstreet.file_service.service.FileService;
import com.careerstreet.file_service.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        FileData fileData = fileRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .fileData(FileUtils.compressImage(file.getBytes())).build()
        );
        if(fileData!= null){
            return "file upload success "+ file.getOriginalFilename();
        }
        return null;
    }

    @Override
    public byte[] downloadFile(Long id){
        Optional<FileData> fileData = fileRepository.findByFileDataId(id);
        byte[] files = FileUtils.decompressImage(fileData.get().getFileData());
        return files;
    }


}

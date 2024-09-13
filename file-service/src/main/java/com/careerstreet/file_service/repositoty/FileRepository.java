package com.careerstreet.file_service.repositoty;

import com.careerstreet.file_service.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileData, Long> {
    Optional<FileData> findByFileDataId(Long id);
}

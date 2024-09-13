package com.careerstreet.file_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileDataId")
    private Long fileDataId;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "type", nullable = true)
    private String type;

    @Lob
    @Column(name = "fileData" , length = 5000)
    private byte[] fileData;
}

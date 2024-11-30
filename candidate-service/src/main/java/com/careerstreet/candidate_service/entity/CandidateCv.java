package com.careerstreet.candidate_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class CandidateCv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidateCv_id")
    private Long candidateCvId;

    @Column(name = "full_name", nullable = true)
    private String fullName;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "school", nullable = true)
    private String school;

    @Column(name = "language", nullable = true)
    private String language;

    @Column(name = "experience", nullable = true)
    private String experience;

    @Column(name = "title", nullable = true)
    private String title;

    @Column(name = "currentSalary", nullable = true)
    private String currentSalary;

    @Column(name = "preferenceSalary", nullable = true)
    private String preferenceSalary;

    @Column(name = "level_id", nullable = true)
    private Long levelId;

    @Column(name = "positionType", nullable = true)
    private String positionType;

    @Column(name = "workLocation", nullable = true)
    private String workLocation;

    @Column(name = "filePath", nullable = true)
    private String filePath;

    // Thêm thuộc tính khóa ngoại liên kết với bảng Candidate
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

}

package com.careerstreet.candidate_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "experience", nullable = true)
    private String experience;

    @Column(name = "link", nullable = true)
    private String link;

    // Thêm thuộc tính khóa ngoại liên kết với bảng Candidate
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

}

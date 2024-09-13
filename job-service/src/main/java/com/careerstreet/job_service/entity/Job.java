package com.careerstreet.job_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 5000)
    private String description;

    @Lob
    @Column(name = "requirement", length = 5000)
    private String requirement;

    @Column(name = "salary")
    private String salary;

    @Column(name = "address")
    private String address;

    @Column(name = "workType")
    private String workType;

    @Column(name = "posting_date")
    private LocalDate postingDate;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "techDetail_id")
    private Long techDetailId;

    @Column(name = "employer_id")
    private Long employerId;

    // Thêm thuộc tính khóa ngoại liên kết với bảng Candidate
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private Level level;
}

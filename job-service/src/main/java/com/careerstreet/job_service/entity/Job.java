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

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "number_of_employees")
    private Long numberOfEmployees;

    @Column(name = "company_website")
    private String companyWebsite;

    @Lob
    @Column(name = "company_overview", length = 5000)
    private String companyOverview;

    @Column(name = "title")
    private String title;

    @Column(name = "location", length = 500) // Có thể thay đổi nếu cần
    private String jobLocation;

    @Column(name = "salary")
    private Long salary;

    @Lob
    @Column(name = "description", length = 5000)
    private String jobDescription;

    @Lob
    @Column(name = "requirement", length = 5000)
    private String jobRequirements;

    @Column(name = "benefits", length = 5000)
    private String benefits;

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "number_of_recruitment")
    private Long numberOfRecruitment;

    @Column(name = "job_rank")
    private String jobRank;

    @Column(name = "position_type")
    private String jobType;

    @Column(name = "gender")
    private String gender;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_address", length = 500)
    private String contactAddress;

    @Column(name = "posting_date")
    private LocalDate postingDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "status")
    private Long status;

    @Column(name = "views", columnDefinition = "bigint default 0")
    private Long views;

    @Column(name = "employer_id")
    private Long employerId;

    // Thêm thuộc tính khóa ngoại liên kết với bảng Candidate
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private Level level;
}

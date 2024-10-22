package com.careerstreet.apply_service.entity;

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
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long applyId;

    @Column(name = "candidateCv_id")
    private Long candidateCvId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "coverLetter", length = 5000)
    private String coverLetter;

    @Column(name = "status")
    private int status;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now(); // Gán giá trị ngày hiện tại
    }
}

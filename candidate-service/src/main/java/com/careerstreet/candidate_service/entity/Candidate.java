package com.careerstreet.candidate_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private Long candidateId;

    @Column(name = "full_name", nullable = true)
    private String fullName;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "gender", nullable = true)
    private boolean gender; // 0 nu
                            // 1 nam

    @Column(name = "avatar", nullable = true)
    private String avatar;

    @Column(name = "birthday", nullable = true)
    private LocalDate birthday;

    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    // Thêm thuộc tính cho mối quan hệ 1-n
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CandidateCv> candidateCvs;
}

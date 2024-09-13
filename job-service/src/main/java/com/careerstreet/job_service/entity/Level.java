package com.careerstreet.job_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long levelId;

    @Column(name = "name", nullable = true)
    private String name;

    // Thêm thuộc tính cho mối quan hệ 1-n
    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Job> jobs;
}

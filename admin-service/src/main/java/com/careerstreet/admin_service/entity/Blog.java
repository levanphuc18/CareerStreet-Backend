package com.careerstreet.admin_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.w3c.dom.Text;

import java.awt.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long blogId;

    @Column(name = "author", nullable = true)
    private String author;

    @Column(name = "title", nullable = true)
    private String title;

    @Lob
    @Column(name = "content", nullable = true, columnDefinition = "TEXT")
    private String content;

    @Column(name = "date", nullable = true)
    private LocalDate date;

    @Column(name = "origin", nullable = true)
    private String origin;

    // Thêm thuộc tính khóa ngoại liên kết với bảng Candidate
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;
}

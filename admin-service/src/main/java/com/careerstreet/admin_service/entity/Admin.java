package com.careerstreet.admin_service.entity;

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
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "full_name", nullable = true)
    private String fullName;

    @Column(name = "avater", nullable = true)
    private String avatar;

    @Column(name = "contact", nullable = true)
    private String contact;

    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    // Thêm thuộc tính cho mối quan hệ 1-n
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Blog> blogs;
}

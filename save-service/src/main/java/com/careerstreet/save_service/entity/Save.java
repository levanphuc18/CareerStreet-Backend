package com.careerstreet.save_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Data
@Entity
@Table(name = "save")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Save {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saveId;

    private Date date;
    private Long candidateId;
    private Long jobId;
}

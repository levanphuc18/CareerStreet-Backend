package com.careerstreet.tech_service.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class TechDetail {
    @EmbeddedId
    private TechDetailId id;

    @ManyToOne
    @MapsId("techId")
    @JoinColumn(name = "tech_id")
    private Tech tech;
}

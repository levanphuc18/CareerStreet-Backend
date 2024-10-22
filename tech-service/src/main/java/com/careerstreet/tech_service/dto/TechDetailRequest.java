package com.careerstreet.tech_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class TechDetailRequest {
    Long jobId;
    List<Long> techIds;
}

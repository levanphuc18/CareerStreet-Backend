package com.careerstreet.save_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveResponse {

    private Long saveId;
    private Long candidateId;
    private Long jobId;
    private Date date;
}

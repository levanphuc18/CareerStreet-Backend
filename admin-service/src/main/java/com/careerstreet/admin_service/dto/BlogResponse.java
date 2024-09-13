package com.careerstreet.admin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import java.awt.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BlogResponse {
    Long blogId;
    String author;
    String title;
    String content;
    LocalDate date;
    String origin;
    Long admin_id;
}

package com.careerstreet.admin_service.dto;

import lombok.Data;
import org.w3c.dom.Text;

import java.awt.*;
import java.time.LocalDate;

@Data
public class BlogRequest {
    String author;
    String title;
    String content;
    LocalDate date;
    String origin;
    Long admin_id;
}

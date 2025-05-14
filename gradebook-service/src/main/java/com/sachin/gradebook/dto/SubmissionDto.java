package com.sachin.gradebook.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionDto {
    private Long id;
    private Long userId;
    private Long assignmentId;
    private String content;
    private String fileUrl;
    private LocalDateTime submittedAt;
    private Double score;
    private String feedback;
    private String status;
}

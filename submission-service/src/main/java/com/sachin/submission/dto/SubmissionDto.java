package com.sachin.submission.dto;

import com.sachin.submission.entity.SubmissionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionDto {

    private Long id;

    private Long userId;

    private Long assignmentId;

    private String content;

    private String fileUrl;

    private LocalDateTime submittedAt;

    private Integer score;

    private String feedback;

    private SubmissionStatus status;
}

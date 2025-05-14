package com.sachin.submission.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long userId;


    private Long assignmentId;

    @Column(columnDefinition = "TEXT")
    private String content;


    private String fileUrl;


    private LocalDateTime submittedAt;


    private Integer score;

    // Submission status (SUBMITTED, GRADED, LATE, etc.)
    private SubmissionStatus status;

    private String feedback;
}

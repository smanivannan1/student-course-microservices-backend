package com.sachin.gradebook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradebookDto {
    private Long assignmentId;
    private String assignmentName;
    private double maxPoints;
    private double earnedPoints;

    private String status;
    private LocalDateTime submittedAt;
    private LocalDate dueDate;


}
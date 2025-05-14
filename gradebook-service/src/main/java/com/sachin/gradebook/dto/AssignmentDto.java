package com.sachin.gradebook.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AssignmentDto {
    private Long id;
    private Long courseId;
    private String title;
    private String instructions;
    private LocalDate dueDate;
    private double maxPoints;
}

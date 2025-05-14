package com.sachin.assignment.dto;

import lombok.*;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class AssignmentDto {
    private Long id;
    private Long courseId;
    private String title;
    private String instructions;
    private LocalDate dueDate;
    private Integer maxPoints;
    }


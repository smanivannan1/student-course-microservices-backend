package com.sachin.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {

    private Long studentId;
    private Long courseId;
    private LocalDateTime enrollmentDate;
    private String status;
}

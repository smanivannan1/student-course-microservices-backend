package com.sachin.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Long id;
    private String courseName;
    private String courseDescription;
    private String courseCode;
    private String courseSubject;
}

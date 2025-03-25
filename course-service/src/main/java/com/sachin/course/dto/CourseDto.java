package com.sachin.course.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CourseDto {

    private Long id;
    private String courseName;
    private String courseDescription;

    private String courseCode;

    private String courseSubject;
}

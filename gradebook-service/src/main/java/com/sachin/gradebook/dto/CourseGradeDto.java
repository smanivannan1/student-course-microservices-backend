package com.sachin.gradebook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseGradeDto {
    private Long courseId;
    private List<GradebookDto> assignments;
    private double totalEarned;
    private double totalPossible;
    private double percentage;

    private String letterGrade;

    private String gradebookCourseCode;
    private String gradebookCourseName;


    public CourseGradeDto(Long courseId, List<GradebookDto> assignments,
                          double totalEarned, double totalPossible,
                          double percentage, String letterGrade) {
        this.courseId = courseId;
        this.assignments = assignments;
        this.totalEarned = totalEarned;
        this.totalPossible = totalPossible;
        this.percentage = percentage;
        this.letterGrade = letterGrade;
        this.gradebookCourseCode = null;
        this.gradebookCourseName = null;
    }
}



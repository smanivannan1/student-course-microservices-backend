package com.sachin.gradebook.mapper;

import com.sachin.gradebook.dto.CourseGradeDto;
import com.sachin.gradebook.entity.Gradebook;

import java.util.List;

public class GradebookMapper {

    public static CourseGradeDto mapToCourseGradeDto(Gradebook gradebook, List<com.sachin.gradebook.dto.GradebookDto> assignments) {
        return new CourseGradeDto(
                gradebook.getCourseId(),
                assignments,
                gradebook.getTotalEarned(),
                gradebook.getTotalPossible(),
                gradebook.getPercentage(),
                gradebook.getLetterGrade()
        );
    }

    public static Gradebook mapToGradebook(CourseGradeDto courseGradeDto, Long userId) {
        return new Gradebook(
                null, // ID will be generated
                userId,
                courseGradeDto.getCourseId(),
                courseGradeDto.getTotalEarned(),
                courseGradeDto.getTotalPossible(),
                courseGradeDto.getPercentage(),
                courseGradeDto.getLetterGrade()
        );
    }
}

package com.sachin.gradebook.service;

import com.sachin.gradebook.dto.CourseGradeDto;

import java.util.List;

public interface GradebookService {

    CourseGradeDto computeGradeForCourse(Long userId, Long courseId);

    List<CourseGradeDto> getAllGradesForUser(Long userId);

}
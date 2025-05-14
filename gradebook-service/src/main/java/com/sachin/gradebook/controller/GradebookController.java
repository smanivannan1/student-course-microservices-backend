package com.sachin.gradebook.controller;

import com.sachin.gradebook.dto.CourseGradeDto;
import com.sachin.gradebook.service.GradebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gradebook")
@RequiredArgsConstructor
public class GradebookController {

    private final GradebookService gradebookService;



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CourseGradeDto>> getAllGradesForUser(@PathVariable Long userId) {
        List<CourseGradeDto> grades = gradebookService.getAllGradesForUser(userId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/{userId}/course/{courseId}")
    public ResponseEntity<CourseGradeDto> getCourseGrade(
            @PathVariable Long userId,
            @PathVariable Long courseId) {

        CourseGradeDto courseGrade = gradebookService.computeGradeForCourse(userId, courseId);
        return ResponseEntity.ok(courseGrade);
    }
}

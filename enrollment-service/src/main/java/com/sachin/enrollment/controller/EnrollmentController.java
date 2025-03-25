package com.sachin.enrollment.controller;

import com.sachin.enrollment.dto.EnrollmentDto;
import com.sachin.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/{studentId}/{courseId}")
    public ResponseEntity<EnrollmentDto> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {
        EnrollmentDto savedEnrollment = enrollmentService.enrollStudent(studentId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnrollment);
    }

    @DeleteMapping("/{studentId}/{courseId}")
    public ResponseEntity<String> unenrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {
        String result = enrollmentService.unenrollStudent(studentId, courseId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<EnrollmentDto>> getEnrolledCoursesForStudent(
            @PathVariable Long studentId
    ) {
        List<EnrollmentDto> list = enrollmentService.getEnrolledCoursesforStudent(studentId);
        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }
}

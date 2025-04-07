package com.sachin.enrollment.controller;

import com.sachin.enrollment.dto.CourseDto;
import com.sachin.enrollment.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.sachin.enrollment.dto.EnrollmentDto;
import com.sachin.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PreAuthorize("hasRole('STUDENT') or hasRole('INSTRUCTOR')")
    @PostMapping("/{studentId}/{courseId}")
    public ResponseEntity<EnrollmentDto> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {
        EnrollmentDto savedEnrollment = enrollmentService.enrollStudent(studentId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnrollment);
    }
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('STUDENT')")
    @DeleteMapping("/{studentId}/{courseId}")
    public ResponseEntity<String> unenrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {
        String result = enrollmentService.unenrollStudent(studentId, courseId);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('STUDENT') or hasRole('INSTRUCTOR')")
    @GetMapping("/{studentId}")
    public ResponseEntity<List<CourseDto>> getEnrolledCoursesForStudent(
            @PathVariable Long studentId
    ) {
        List<CourseDto> list = enrollmentService.getEnrolledCoursesforStudent(studentId);
        return ResponseEntity.ok(list);
    }


    @PreAuthorize("hasRole('INSTRUCTOR')")
    @GetMapping
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollments() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUTH ROLES: " + auth.getAuthorities());
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/test/student")
    public ResponseEntity<String> testStudentAccess() {
        return ResponseEntity.ok("STUDENT access confirmed.");
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @GetMapping("/test/instructor")
    public ResponseEntity<String> testInstructorAccess() {
        return ResponseEntity.ok("INSTRUCTOR access confirmed.");
    }
}


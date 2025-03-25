package com.sachin.enrollment.service;

import com.sachin.enrollment.dto.EnrollmentDto;

import java.util.List;

public interface EnrollmentService {

    EnrollmentDto enrollStudent(Long studentId, Long courseId);

    String unenrollStudent(Long studentId, Long courseId);

    List<EnrollmentDto> getEnrolledCoursesforStudent(Long studentId);

    List<EnrollmentDto> getAllEnrollments();

}

package com.sachin.enrollment.service;

import com.sachin.enrollment.dto.CourseDto;
import com.sachin.enrollment.dto.EnrollmentDto;
import com.sachin.enrollment.dto.UserDto;

import java.util.List;

public interface EnrollmentService {

    EnrollmentDto enrollStudent(Long studentId, Long courseId);

    String unenrollStudent(Long studentId, Long courseId);

    List<CourseDto> getEnrolledCoursesforStudent(Long studentId);

    List<EnrollmentDto> getAllEnrollments();

}

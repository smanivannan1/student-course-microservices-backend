package com.sachin.enrollment.mapper;

import com.sachin.enrollment.dto.EnrollmentDto;
import com.sachin.enrollment.entity.Enrollment;
import com.sachin.enrollment.entity.EnrollmentStatus;


public final class EnrollmentMapper {

    private EnrollmentMapper() {
        // Prevent instantiation
    }

    public static EnrollmentDto mapToEnrollmentDto(Enrollment enrollment) {
        return new EnrollmentDto(
                enrollment.getUserId(),
                enrollment.getCourseId(),
                enrollment.getEnrollmentDate(),
                enrollment.getStatus().name()
        );
    }

    public static Enrollment mapToEnrollment(EnrollmentDto enrollmentDto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(enrollmentDto.getStudentId());
        enrollment.setCourseId(enrollmentDto.getCourseId());
        enrollment.setEnrollmentDate(enrollmentDto.getEnrollmentDate());
        enrollment.setStatus(EnrollmentStatus.valueOf(enrollmentDto.getStatus()));
        return enrollment;
    }
}

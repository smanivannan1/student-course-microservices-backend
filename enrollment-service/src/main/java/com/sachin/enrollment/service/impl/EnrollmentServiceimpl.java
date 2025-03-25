package com.sachin.enrollment.service.impl;
import java.util.ArrayList;
import java.util.List;
import com.sachin.enrollment.client.CourseClient;
import com.sachin.enrollment.client.CourseClientFeign;
import com.sachin.enrollment.client.UserClient;
import com.sachin.enrollment.client.UserClientFeign;
import com.sachin.enrollment.dto.CourseDto;
import com.sachin.enrollment.dto.EnrollmentDto;
import com.sachin.enrollment.dto.UserDto;
import com.sachin.enrollment.entity.Enrollment;
import com.sachin.enrollment.entity.EnrollmentStatus;
import com.sachin.enrollment.mapper.EnrollmentMapper;
import com.sachin.enrollment.repository.EnrollmentRepository;
import com.sachin.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sachin.enrollment.messaging.EnrollmentEventPublisher;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Service

public class EnrollmentServiceimpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserClientFeign userClient;
    private final CourseClientFeign courseClient;
    private final EnrollmentEventPublisher enrollmentEventPublisher;

    @Override
    public EnrollmentDto enrollStudent(Long studentId, Long courseId) {
        UserDto student = userClient.getUserById(studentId);
        if (!student.getRole().equalsIgnoreCase("STUDENT")) {
            throw new IllegalStateException("Only students may enroll in courses");
        }

        CourseDto course = courseClient.getCourseById(courseId);

        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        enrollmentEventPublisher.sendEnrollmentNotification(
                student.getName() + " has sucessfully been enrolled in course " + course.getCourseCode()+": "+ course.getCourseName()
        );
        return EnrollmentMapper.mapToEnrollmentDto(savedEnrollment);
    }

    @Override
    public String unenrollStudent(Long studentId, Long courseId) {
        // ✅ Validate that the student exists and is a student
        UserDto student = userClient.getUserById(studentId);
        if (!student.getRole().equalsIgnoreCase("STUDENT")) {
            throw new IllegalStateException("Only students can be unenrolled from courses.");
        }

        // ✅ Validate that the course exists
        CourseDto course = courseClient.getCourseById(courseId);
        // ✅ Find enrollments by userId and courseId
        List<Enrollment> enrollments = enrollmentRepository.findByUserIdAndCourseId(studentId, courseId);
        if (enrollments.isEmpty()) {
            throw new IllegalStateException("No enrollment found for student ID: " + studentId + " and course ID: " + courseId);
        }
        boolean alreadyUnenrolled = true;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                enrollment.setStatus(EnrollmentStatus.CANCELLED);
                enrollmentRepository.save(enrollment);
                alreadyUnenrolled = false;
            }
        }
        if (alreadyUnenrolled) {

            String alreadyUnenrolledMessage = student.getName() + " is already unenrolled from this course.";
            enrollmentEventPublisher.sendEnrollmentNotification(alreadyUnenrolledMessage);
            return alreadyUnenrolledMessage;
        }

        String unenrollmentMessage = student.getName() + " has been successfully unenrolled from " + course.getCourseName();
        enrollmentEventPublisher.sendEnrollmentNotification(unenrollmentMessage);
        return unenrollmentMessage;
    }

    @Override
    public List<EnrollmentDto> getEnrolledCoursesforStudent(Long studentId) {
        // ✅ Validate that the student exists
        UserDto student = userClient.getUserById(studentId);
        if (!student.getRole().equalsIgnoreCase("STUDENT")) {
            throw new IllegalStateException("Only students can have course enrollments.");
        }

        // ✅ Fetch ACTIVE enrollments for the student
        List<Enrollment> enrollmentList = enrollmentRepository.findByUserIdAndStatus(studentId, EnrollmentStatus.ACTIVE);
        if (enrollmentList.isEmpty()) {
            throw new IllegalStateException("No active enrollments found for student ID: " + studentId);
        }

        // ✅ Map to DTOs
        return enrollmentList.stream()
                .map(EnrollmentMapper::mapToEnrollmentDto)
                .toList();
    }

    @Override
    public List<EnrollmentDto> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findByStatus(EnrollmentStatus.ACTIVE);

        if (enrollments.isEmpty()) {
            throw new IllegalStateException("No active enrollments found.");
        }

        return enrollments.stream()
                .map(EnrollmentMapper::mapToEnrollmentDto)
                .toList();
    }



}
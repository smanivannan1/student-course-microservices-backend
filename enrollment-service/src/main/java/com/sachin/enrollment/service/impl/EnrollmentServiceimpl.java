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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sachin.enrollment.messaging.EnrollmentEventPublisher;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Optional<Enrollment> existing = enrollmentRepository
                .findByUserIdAndCourseIdAndStatus(studentId, courseId, EnrollmentStatus.ACTIVE);

        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Student is already enrolled in this course.");
        }

        CourseDto course = courseClient.getCourseById(courseId);

        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        String message = student.getName() + " has successfully been enrolled in course " +
                course.getCourseCode() + ": " + course.getCourseName();

        try {
            enrollmentEventPublisher.sendEnrollmentNotification(message);
        } catch (Exception e) {
            System.err.println("Failed to send enrollment notification: " + e.getMessage());
        }

        return EnrollmentMapper.mapToEnrollmentDto(savedEnrollment);
    }

    @Override
    public String unenrollStudent(Long studentId, Long courseId) {
        UserDto student = userClient.getUserById(studentId);
        if (!student.getRole().equalsIgnoreCase("STUDENT")) {
            throw new IllegalStateException("Only students can be unenrolled from courses.");
        }

        CourseDto course = courseClient.getCourseById(courseId);
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

        String message;
        if (alreadyUnenrolled) {
            message = student.getName() + " is already unenrolled from this course.";
        } else {
            message = student.getName() + " has been successfully unenrolled from " + course.getCourseName();
        }

        //Protect the event publishing
        try {
            enrollmentEventPublisher.sendEnrollmentNotification(message);
        } catch (Exception e) {
            System.err.println("⚠️ Failed to send unenrollment notification: " + e.getMessage());
        }

        return message;
    }

    @Override
    public List<CourseDto> getEnrolledCoursesforStudent(Long studentId) {
        // Validate that the student exists
        UserDto student = userClient.getUserById(studentId);
        if (!student.getRole().equalsIgnoreCase("STUDENT")) {
            throw new IllegalStateException("Only students can have course enrollments.");
        }

        //Fetch ACTIVE enrollments for the student
        List<Enrollment> enrollmentList = enrollmentRepository.findByUserIdAndStatus(studentId, EnrollmentStatus.ACTIVE);
        if (enrollmentList.isEmpty()) {
            throw new IllegalStateException("No active enrollments found for student ID: " + studentId);
        }
        // Map to DTOs
        return enrollmentList.stream()
                .map(enrollment -> courseClient.getCourseById(enrollment.getCourseId()))
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

    @Override
    public List<UserDto> getStudentsByCourseId(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseIdAndStatus(courseId, EnrollmentStatus.ACTIVE);

        List<Long> userIds = enrollments.stream()
                .map(Enrollment::getUserId)
                .collect(Collectors.toList());

        List<UserDto> allUsers = userClient.getUsersByIds(userIds);

        return allUsers.stream()
                .filter(user -> "STUDENT".equalsIgnoreCase(user.getRole()))
                .collect(Collectors.toList());
    }

}

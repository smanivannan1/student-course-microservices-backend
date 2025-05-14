package com.sachin.enrollment.repository;

import com.sachin.enrollment.entity.Enrollment;
import com.sachin.enrollment.entity.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    List<Enrollment> findByUserIdAndStatus(Long userId, EnrollmentStatus status);

    List<Enrollment> findByUserId(Long userId);

    List<Enrollment> findByStatus(EnrollmentStatus status);

    Optional<Enrollment> findByUserIdAndCourseIdAndStatus(Long userId, Long courseId, EnrollmentStatus status);

    List<Enrollment> findByCourseId(Long courseId);

    List<Enrollment> findByCourseIdAndStatus(Long courseId, EnrollmentStatus status);
}

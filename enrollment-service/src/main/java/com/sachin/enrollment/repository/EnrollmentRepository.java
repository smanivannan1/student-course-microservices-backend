package com.sachin.enrollment.repository;

import com.sachin.enrollment.entity.Enrollment;
import com.sachin.enrollment.entity.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    List<Enrollment> findByUserIdAndStatus(Long userId, EnrollmentStatus status);

    List<Enrollment> findByUserId(Long userId);

    List<Enrollment> findByStatus(EnrollmentStatus status);
}

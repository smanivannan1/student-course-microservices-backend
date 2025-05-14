package com.sachin.gradebook.repository;

import com.sachin.gradebook.entity.Gradebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradebookRepository extends JpaRepository<Gradebook, Long> {
    Optional<Gradebook> findByUserIdAndCourseId(Long userId, Long courseId);

    List<Gradebook> findAllByUserId(Long userId);

}

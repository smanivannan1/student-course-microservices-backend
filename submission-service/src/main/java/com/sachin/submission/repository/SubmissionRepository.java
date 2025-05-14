package com.sachin.submission.repository;

import com.sachin.submission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {


    List<Submission> findByUserId(Long userId);

    List<Submission> findByAssignmentId(Long assignmentId);

    Optional<Submission> findByUserIdAndAssignmentId(Long userId, Long assignmentId);


    List<Submission> findAllByUserIdAndAssignmentId(Long userId, Long assignmentId);


    List<Submission> findByUserIdAndAssignmentIdIn(Long userId, List<Long> assignmentIds);
}

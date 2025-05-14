package com.sachin.submission.service;

import com.sachin.submission.dto.SubmissionDto;
import com.sachin.submission.entity.Submission;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SubmissionService {

    SubmissionDto createSubmission(Long userId, Long assignmentId, String content, MultipartFile file) throws IOException;

    List<SubmissionDto> getSubmissionsByUser(Long userId);

    List<SubmissionDto> getSubmissionsByAssignment(Long assignmentId);

    SubmissionDto getSubmission(Long userId, Long assignmentId);

    SubmissionDto gradeSubmission(Long submissionId, int score, String feedback);

    List<SubmissionDto> getSubmissionsForStudentInCourse(Long userId, Long courseId);

}

package com.sachin.submission.service;
import java.util.stream.Collectors;
import com.sachin.submission.client.AssignmentClientFeign;
import com.sachin.submission.client.UserClientFeign;
import com.sachin.submission.dto.AssignmentDto;
import com.sachin.submission.dto.SubmissionDto;
import com.sachin.submission.dto.UserDto;
import com.sachin.submission.entity.Submission;
import com.sachin.submission.entity.SubmissionStatus;
import com.sachin.submission.mapper.SubmissionMapper;
import com.sachin.submission.repository.SubmissionRepository;
import com.sachin.submission.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserClientFeign userClient;
    private final AssignmentClientFeign assignmentClient;

    @Override
    public SubmissionDto createSubmission(Long userId, Long assignmentId, String content, MultipartFile file) {
        // Validate assignment
        AssignmentDto assignment = assignmentClient.getAssignmentById(assignmentId);

        // Determine submission status
        SubmissionStatus status = SubmissionStatus.SUBMITTED;
        if (assignment.getDueDate() != null && LocalDate.now().isAfter(assignment.getDueDate())) {
            status = SubmissionStatus.LATE;
        }

        // ✅ Remove old submissions for this user & assignment
        List<Submission> existingSubmissions = submissionRepository.findAllByUserIdAndAssignmentId(userId, assignmentId);
        existingSubmissions.forEach(submissionRepository::delete);

        // ✅ Create new submission
        Submission submission = new Submission();
        submission.setUserId(userId);
        submission.setAssignmentId(assignmentId);
        submission.setContent(content);
        submission.setFileUrl(null); // Placeholder for file support
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setStatus(status);

        Submission saved = submissionRepository.save(submission);
        return SubmissionMapper.toDto(saved);
    }

    @Override
    public List<SubmissionDto> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUserId(userId)
                .stream()
                .map(SubmissionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubmissionDto> getSubmissionsByAssignment(Long assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId)
                .stream()
                .map(SubmissionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionDto getSubmission(Long userId, Long assignmentId) {
        return submissionRepository.findByUserIdAndAssignmentId(userId, assignmentId)
                .map(SubmissionMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Submission not found"));
    }

    @Override
    public SubmissionDto gradeSubmission(Long submissionId, int score, String feedback) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        submission.setScore(score);
        submission.setFeedback(feedback);
        submission.setStatus(SubmissionStatus.GRADED);

        Submission saved = submissionRepository.save(submission);

        // ✅ Add log for debugging
        System.out.println("✅ Graded Submission: ID = " + saved.getId() +
                " | Score = " + saved.getScore() +
                " | Status = " + saved.getStatus());

        return SubmissionMapper.toDto(saved);
    }

    @Override
    public List<SubmissionDto> getSubmissionsForStudentInCourse(Long userId, Long courseId) {
        List<AssignmentDto> assignments = assignmentClient.getAssignmentsByCourseId(courseId);

        List<Long> assignmentIds = assignments.stream()
                .map(AssignmentDto::getId)
                .toList();

        List<Submission> submissions = submissionRepository.findByUserIdAndAssignmentIdIn(userId, assignmentIds);

        return submissions.stream()
                .map(SubmissionMapper::toDto)
                .toList();
    }
}

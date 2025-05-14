package com.sachin.submission.controller;

import com.sachin.submission.dto.SubmissionDto;
import com.sachin.submission.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/assignments/{assignmentId}")
    public ResponseEntity<SubmissionDto> createSubmission(
            @PathVariable Long assignmentId,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) MultipartFile file
    ) throws IOException {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        SubmissionDto submission = submissionService.createSubmission(userId, assignmentId, content, file);
        return ResponseEntity.ok(submission);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/assignments/{assignmentId}/me")
    public ResponseEntity<SubmissionDto> getMySubmissionForAssignment(@PathVariable Long assignmentId) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        SubmissionDto submission = submissionService.getSubmission(userId, assignmentId);
        return ResponseEntity.ok(submission);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("/{submissionId}/grade")
    public ResponseEntity<SubmissionDto> gradeSubmission(
            @PathVariable Long submissionId,
            @RequestParam int score,
            @RequestParam(required = false) String feedback
    ) {
        SubmissionDto graded = submissionService.gradeSubmission(submissionId, score, feedback);
        return ResponseEntity.ok(graded);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @GetMapping("/assignments/{assignmentId}")
    public ResponseEntity<List<SubmissionDto>> getSubmissionsByAssignment(@PathVariable Long assignmentId) {
        List<SubmissionDto> submissions = submissionService.getSubmissionsByAssignment(assignmentId);
        return ResponseEntity.ok(submissions);
    }

    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('STUDENT')")
    @GetMapping("/student/{userId}/course/{courseId}")
    public ResponseEntity<List<SubmissionDto>> getSubmissionsForStudentInCourse(
            @PathVariable Long userId,
            @PathVariable Long courseId) {
        List<SubmissionDto> submissions = submissionService.getSubmissionsForStudentInCourse(userId, courseId);
        return ResponseEntity.ok(submissions);
    }
}

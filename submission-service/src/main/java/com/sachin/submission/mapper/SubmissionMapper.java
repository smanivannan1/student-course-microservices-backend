package com.sachin.submission.mapper;

import com.sachin.submission.dto.SubmissionDto;
import com.sachin.submission.entity.Submission;

public class SubmissionMapper {

    public static SubmissionDto toDto(Submission submission) {
        SubmissionDto dto = new SubmissionDto();
        dto.setId(submission.getId());
        dto.setUserId(submission.getUserId());
        dto.setAssignmentId(submission.getAssignmentId());
        dto.setContent(submission.getContent());
        dto.setFileUrl(submission.getFileUrl());
        dto.setSubmittedAt(submission.getSubmittedAt());
        dto.setScore(submission.getScore());
        dto.setFeedback(submission.getFeedback());
        dto.setStatus(submission.getStatus());
        return dto;
    }

    public static Submission toEntity(SubmissionDto dto) {
        Submission submission = new Submission();
        submission.setId(dto.getId());
        submission.setUserId(dto.getUserId());
        submission.setAssignmentId(dto.getAssignmentId());
        submission.setContent(dto.getContent());
        submission.setFileUrl(dto.getFileUrl());
        submission.setSubmittedAt(dto.getSubmittedAt());
        submission.setScore(dto.getScore());
        submission.setFeedback(dto.getFeedback());
        submission.setStatus(dto.getStatus());
        return submission;
    }
}


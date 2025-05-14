package com.sachin.gradebook.service;

import com.sachin.gradebook.client.AssignmentClient;
import com.sachin.gradebook.client.EnrollmentClient;
import com.sachin.gradebook.client.SubmissionClient;
import com.sachin.gradebook.dto.*;
import com.sachin.gradebook.entity.Gradebook;
import com.sachin.gradebook.mapper.GradebookMapper;
import com.sachin.gradebook.repository.GradebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradebookServiceImpl implements GradebookService {

    private final SubmissionClient submissionClient;
    private final AssignmentClient assignmentClient;

    private final EnrollmentClient enrollmentClient;
    private final GradebookRepository gradebookRepository;



    @Override
    public CourseGradeDto computeGradeForCourse(Long userId, Long courseId) {

        List<SubmissionDto> submissions = submissionClient.getSubmissionsForStudentInCourse(userId, courseId);

        System.out.println("👀 Submissions for user " + userId + " in course " + courseId + ": " + submissions.size());
        for (SubmissionDto s : submissions) {
            System.out.println("   ↪️ ID: " + s.getId() +
                    " | Status: " + s.getStatus() +
                    " | Score: " + s.getScore() +
                    " | Assignment ID: " + s.getAssignmentId());
        }

        double totalEarned = 0;
        double totalPossible = 0;
        List<GradebookDto> gradebookEntries = new ArrayList<>();

        for (SubmissionDto submission : submissions) {
            if (submission.getStatus() == null || !"GRADED".equalsIgnoreCase(submission.getStatus())) {
                continue;
            }


            AssignmentDto assignment = assignmentClient.getAssignmentById(submission.getAssignmentId());

            double earned = submission.getScore() != null ? submission.getScore() : 0;
            double max = assignment.getMaxPoints();

            totalEarned += earned;
            totalPossible += max;
            String status = submission.getStatus().toString();
            LocalDateTime submittedAt = submission.getSubmittedAt();
            LocalDate dueDate = assignment.getDueDate();

            gradebookEntries.add(new GradebookDto(
                    assignment.getId(),
                    assignment.getTitle(),
                    max,
                    earned,
                    status,
                    submittedAt,
                    dueDate
            ));
        }

        double percentage = totalPossible == 0 ? 0 : (totalEarned / totalPossible) * 100;
        String letterGrade = getLetterGrade(percentage);



        Gradebook gradebook = GradebookMapper.mapToGradebook(
                new CourseGradeDto(courseId, gradebookEntries, totalEarned, totalPossible, percentage, letterGrade),
                userId
        );

        gradebookRepository.save(gradebook);

        // Return DTO to controller
        return GradebookMapper.mapToCourseGradeDto(gradebook, gradebookEntries);
    }

    @Override
    public List<CourseGradeDto> getAllGradesForUser(Long userId) {
        List<CourseDto> enrolledCourses = enrollmentClient.getEnrolledCoursesForStudent(userId);

        List<CourseGradeDto> results = new ArrayList<>();
        for (CourseDto course : enrolledCourses) {
            CourseGradeDto dto = computeGradeForCourse(userId, course.getId());
            dto.setGradebookCourseCode(course.getCourseCode());
            dto.setGradebookCourseName(course.getCourseName());
            // If no graded assignments, mark grade fields as N/A
            if (dto.getAssignments() == null || dto.getAssignments().isEmpty()) {
                dto.setLetterGrade("N/A");
                dto.setPercentage(0.0); // or leave at 0
            }
            results.add(dto);
        }

        return results;
    }
    private String getLetterGrade(double percentage) {
        if (percentage >= 97) return "A+";
        if (percentage >= 93) return "A";
        if (percentage >= 90) return "A-";
        if (percentage >= 87) return "B+";
        if (percentage >= 83) return "B";
        if (percentage >= 80) return "B-";
        if (percentage >= 77) return "C+";
        if (percentage >= 73) return "C";
        if (percentage >= 70) return "C-";
        if (percentage >= 67) return "D+";
        if (percentage >= 63) return "D";
        if (percentage >= 60) return "D-";
        return "F";
    }



}
package com.sachin.gradebook.client;

import com.sachin.gradebook.dto.SubmissionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "submission-service")
public interface SubmissionClient {

    @GetMapping("/api/submissions/student/{userId}/course/{courseId}")
    List<SubmissionDto> getSubmissionsForStudentInCourse(
            @PathVariable("userId") Long userId,
            @PathVariable("courseId") Long courseId
    );

    @GetMapping("/submissions/student/{studentId}/courses")
    List<Long> getCourseIdsForStudent(@PathVariable("studentId") Long studentId);
}

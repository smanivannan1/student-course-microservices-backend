package com.sachin.gradebook.client;

import com.sachin.gradebook.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "enrollment-service")
public interface EnrollmentClient {

    @GetMapping("/api/enrollments/{studentId}")
    List<CourseDto> getEnrolledCoursesForStudent(@PathVariable("studentId") Long studentId);
}

package com.sachin.assignment.client;

import com.sachin.assignment.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service")
public interface CourseClientFeign {

    @GetMapping("/api/courses/{id}")
    CourseDto getCourseById(@PathVariable("id") Long courseId);
}

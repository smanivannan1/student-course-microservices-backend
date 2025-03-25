package com.sachin.enrollment.client;

import com.sachin.enrollment.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service") // Name should match the service name registered in Eureka
public interface CourseClientFeign {

    @GetMapping("/api/courses/{id}")
    CourseDto getCourseById(@PathVariable("id") Long courseId);
}

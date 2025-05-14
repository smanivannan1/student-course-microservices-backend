package com.sachin.gradebook.client;

import com.sachin.gradebook.dto.AssignmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "assignment-service")
public interface AssignmentClient {

    @GetMapping("/api/assignments/{id}")
    AssignmentDto getAssignmentById(@PathVariable("id") Long id);

    @GetMapping("/api/assignments/course/{courseId}")
    List<AssignmentDto> getAssignmentsByCourseId(@PathVariable Long courseId);
}

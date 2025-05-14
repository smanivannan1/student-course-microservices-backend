package com.sachin.assignment.service;

import com.sachin.assignment.dto.AssignmentDto;
import com.sachin.assignment.entity.Assignment;

import java.util.List;

public interface AssignmentService {

    AssignmentDto createAssignment(AssignmentDto assignmentDto);
    AssignmentDto getAssignmentById(Long id);
    List<AssignmentDto> getAssignmentsByCourseId(Long courseId);
    void deleteAssignment(Long id);

    AssignmentDto updateAssignment(Long id, AssignmentDto assignmentDto);

}

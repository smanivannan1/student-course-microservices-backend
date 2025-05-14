package com.sachin.assignment.mapper;

import com.sachin.assignment.dto.AssignmentDto;
import com.sachin.assignment.entity.Assignment;

public class AssignmentMapper {

    public static AssignmentDto mapToAssignmentDto(Assignment assignment) {
        return new AssignmentDto(
                assignment.getId(),
                assignment.getCourseId(),
                assignment.getTitle(),
                assignment.getInstructions(),
                assignment.getDueDate(),
                assignment.getMaxPoints()
        );
    }

    public static Assignment mapToAssignment(AssignmentDto assignmentDto) {
        return new Assignment(
                assignmentDto.getId(),
                assignmentDto.getCourseId(),
                assignmentDto.getTitle(),
                assignmentDto.getInstructions(),
                assignmentDto.getDueDate(),
                assignmentDto.getMaxPoints()
        );
    }
}

package com.sachin.assignment.service;

import com.sachin.assignment.client.CourseClientFeign;
import com.sachin.assignment.dto.AssignmentDto;
import com.sachin.assignment.entity.Assignment;
import com.sachin.assignment.mapper.AssignmentMapper;
import com.sachin.assignment.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;
    private final CourseClientFeign courseClient;


    @Override
    public AssignmentDto createAssignment(AssignmentDto assignmentDto) {
        Assignment assignment = AssignmentMapper.mapToAssignment(assignmentDto);
        Assignment saved = assignmentRepository.save(assignment);
        return AssignmentMapper.mapToAssignmentDto(saved);
    }


    @Override
    public AssignmentDto getAssignmentById(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id " + id));
        return AssignmentMapper.mapToAssignmentDto(assignment);
    }

    @Override
    public List<AssignmentDto> getAssignmentsByCourseId(Long courseId) {
        List<Assignment> assignments = assignmentRepository.findByCourseId(courseId);
        return assignments.stream()
                .map(AssignmentMapper::mapToAssignmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);

    }

    @Override
    public AssignmentDto updateAssignment(Long id, AssignmentDto assignmentDto) {
        Assignment existing = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id " + id));

        existing.setTitle(assignmentDto.getTitle());
        existing.setInstructions(assignmentDto.getInstructions());
        existing.setDueDate(assignmentDto.getDueDate());
        existing.setMaxPoints(assignmentDto.getMaxPoints());

        Assignment updated = assignmentRepository.save(existing);
        return AssignmentMapper.mapToAssignmentDto(updated);
    }

}

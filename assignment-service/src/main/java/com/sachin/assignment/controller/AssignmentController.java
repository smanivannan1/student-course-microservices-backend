package com.sachin.assignment.controller;

import com.sachin.assignment.dto.AssignmentDto;
import com.sachin.assignment.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<AssignmentDto> createAssignment(@RequestBody AssignmentDto assignmentDto) {
        AssignmentDto created = assignmentService.createAssignment(assignmentDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDto> getAssignmentById(@PathVariable Long id) {
        AssignmentDto assignment = assignmentService.getAssignmentById(id);
        return new ResponseEntity<>(assignment, HttpStatus.OK);
    }


    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AssignmentDto>> getAssignmentsByCourseId(@PathVariable Long courseId) {
        List<AssignmentDto> assignments = assignmentService.getAssignmentsByCourseId(courseId);
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDto> updateAssignment(@PathVariable Long id, @RequestBody AssignmentDto dto) {
        AssignmentDto updated = assignmentService.updateAssignment(id, dto);
        return ResponseEntity.ok(updated);
    }

}
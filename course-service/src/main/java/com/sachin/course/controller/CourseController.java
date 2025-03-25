package com.sachin.course.controller;


import com.sachin.course.dto.CourseDto;
import com.sachin.course.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RestController
@RequestMapping("/api/courses")

public class CourseController {
    private CourseService courseService;

    //Create a new course
    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto){
        CourseDto savedUser = courseService.createCourse(courseDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    //Get a list of all courses
    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        List<CourseDto> courseDtoList = courseService.getAllCourses();
        return ResponseEntity.ok(courseDtoList);
    }

    //Find a course by ID
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCoursebyID(@PathVariable("id") Long courseId){
        CourseDto courseDto = courseService.getCoursebyId(courseId);
        return ResponseEntity.ok(courseDto);
    }

    //Update a course

    @PutMapping("/{id}")

    public ResponseEntity<CourseDto> updateCourse(@PathVariable("id") Long id, @RequestBody CourseDto updatedCourse){
        CourseDto courseDto = courseService.updateCourse(id, updatedCourse);
        return ResponseEntity.ok(courseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteCourse(@PathVariable("id") Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course with ID number:" + id + " has been deleted");

    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseDto>> searchCourses(@RequestParam String query){
        List<CourseDto> courseDtoList = courseService.searchByCodeOrSubject(query);
        return ResponseEntity.ok(courseDtoList);
    }

    @GetMapping("/searchfor")
    public ResponseEntity<List<CourseDto>> searchCoursesbySubject(@RequestParam String courseSubject){
        List<CourseDto> courseDtoList = courseService.searchCoursesbySubject(courseSubject);
        return ResponseEntity.ok(courseDtoList);
    }


}

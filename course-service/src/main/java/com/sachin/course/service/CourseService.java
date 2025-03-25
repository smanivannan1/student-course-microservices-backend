package com.sachin.course.service;

import com.sachin.course.dto.CourseDto;
import com.sachin.course.entity.Course;

import java.util.List;

public interface CourseService {

    CourseDto createCourse(CourseDto courseDto);
    CourseDto getCoursebyId(Long id);
    List<CourseDto> getAllCourses();
    CourseDto updateCourse(Long courseId, CourseDto courseDto);
    void deleteCourse(Long id);
    List<CourseDto> searchCoursesbyCode(String courseCode);
    List<CourseDto> searchCoursesbySubject(String courseSubject);

    List<CourseDto> searchByCodeOrSubject(String query);








}

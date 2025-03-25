package com.sachin.course.mapper;

import com.sachin.course.dto.CourseDto;
import com.sachin.course.entity.Course;

public class CourseMapper {
    public static CourseDto maptoCourseDto(Course course){
        return new CourseDto(course.getId(), course.getCourseName(), course.getCourseDescription(), course.getCourseCode(), course.getCourseSubject());
    }

    public static Course maptoCourse(CourseDto courseDto){
        return new Course(courseDto.getId(), courseDto.getCourseName(), courseDto.getCourseDescription(), courseDto.getCourseCode(), courseDto.getCourseSubject());
    }
}
package com.sachin.course.service.impl;
import com.sachin.course.dto.CourseDto;
import com.sachin.course.entity.Course;
import com.sachin.course.exception.ResourceNotFoundException;
import com.sachin.course.mapper.CourseMapper;
import com.sachin.course.repository.CourseRepository;
import com.sachin.course.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.sachin.course.messaging.CourseEventPublisher;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServiceimpl implements CourseService {

    private CourseRepository courseRepository;
    private CourseEventPublisher courseEventPublisher;
    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = CourseMapper.maptoCourse(courseDto);
        Course savedCourse = courseRepository.save(course);
        courseEventPublisher.sendCourseNotification
                ("New course created: " + savedCourse.getCourseCode() + " " + savedCourse.getCourseName());
        return CourseMapper.maptoCourseDto(savedCourse);

    }

    @Override
    public CourseDto getCoursebyId(Long courseid) {
        Course course = courseRepository.findById(courseid)
                .orElseThrow(() -> new ResourceNotFoundException("Course does not exist with given ID:" + courseid ));
        return CourseMapper.maptoCourseDto(course);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> CourseList = courseRepository.findAll();
        List<CourseDto> CourseDtoList = new ArrayList<>();
        for(int i = 0; i < CourseList.size(); i++){
            Course course = CourseList.get(i);
            CourseDto courseDto = CourseMapper.maptoCourseDto(course);
            CourseDtoList.add(courseDto);
        }
        return CourseDtoList;

    }

    @Override
    public CourseDto updateCourse(Long courseId, CourseDto updatedCourse) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()->new ResourceNotFoundException("Course does not exist with given ID:" + courseId));

        course.setCourseName(updatedCourse.getCourseName());
        course.setCourseDescription(updatedCourse.getCourseDescription());
        course.setCourseCode(updatedCourse.getCourseCode());
        course.setCourseSubject(updatedCourse.getCourseSubject());

        Course updatedCourseobj = courseRepository.save(course);

        return CourseMapper.maptoCourseDto(updatedCourseobj);


    }

    @Override
    public void deleteCourse(Long courseid) {
        Course course = courseRepository.findById(courseid)
                .orElseThrow(()->new ResourceNotFoundException("Course does not exist with given ID:" + courseid));
        courseRepository.delete(course);


    }

    @Override
    public List<CourseDto> searchCoursesbyCode(String courseCode) {
        System.out.println("Received courseCode: '" + courseCode + "'");

        // Change this:
        List<Course> CourseList = courseRepository.findByCourseCodeIgnoreCase(courseCode.trim()); // ✅ Removed IgnoreCase
        if (CourseList.isEmpty()) {
            System.out.println("No courses found with courseCode: '" + courseCode + "'");
        } else {
            for (Course course : CourseList) {
                System.out.println("Found Course: " + course.getCourseCode() + " - " + course.getCourseName());
            }
        }

        return CourseList.stream()
                .map(CourseMapper::maptoCourseDto)
                .toList();
    }

    @Override
    public List<CourseDto> searchCoursesbySubject(String courseSubject) {

        List<Course> courseList = courseRepository.findByCourseSubjectContainingIgnoreCase(courseSubject.trim());
        List<CourseDto> CourseDtoList = new ArrayList<>();
        for(int i = 0; i < courseList.size(); i++){
            Course course = courseList.get(i);
            CourseDto courseDto = CourseMapper.maptoCourseDto(course);
            CourseDtoList.add(courseDto);
        }
        return CourseDtoList;

    }

    @Override
    public List<CourseDto> searchByCodeOrSubject(String query) {
        List<Course> courses = courseRepository.findByCourseCodeContainingIgnoreCaseOrCourseSubjectContainingIgnoreCase(query, query);
        return courses.stream()
                .map(CourseMapper::maptoCourseDto)
                .collect(Collectors.toList());
    }


}

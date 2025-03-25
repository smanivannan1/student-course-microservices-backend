package com.sachin.course.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sachin.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCourseCodeIgnoreCase(String courseCode);

    List<Course> findByCourseSubjectContainingIgnoreCase(String courseSubject);

    List<Course> findByCourseCodeContainingIgnoreCaseOrCourseSubjectContainingIgnoreCase(String courseCode, String subject);


}


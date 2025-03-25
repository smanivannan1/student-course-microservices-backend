package com.sachin.enrollment.client;

import com.sachin.enrollment.dto.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CourseClient {

    private final RestTemplate restTemplate;

    @Autowired
    public CourseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CourseDto getCourseById(Long courseId) {
        String url = "http://course-service/api/courses/" + courseId;
        System.out.println("Fetching course with URL: " + url);
        return restTemplate.getForObject(url, CourseDto.class);
    }

}

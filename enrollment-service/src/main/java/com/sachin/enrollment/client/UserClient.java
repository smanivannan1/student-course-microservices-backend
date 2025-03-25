package com.sachin.enrollment.client;

import com.sachin.enrollment.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    private final String userServiceBaseUrl;

    public UserClient(RestTemplate restTemplate,
                      @Value("${user.service.url}") String userServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.userServiceBaseUrl = userServiceBaseUrl;
    }

    public UserDto getUserById(Long studentId) {
        String url = "http://user-service/api/users/" + studentId;
        System.out.println("Fetching user with URL: " + url);
        return restTemplate.getForObject(url, UserDto.class);
    }

}

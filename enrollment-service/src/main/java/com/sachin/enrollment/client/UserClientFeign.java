package com.sachin.enrollment.client;

import com.sachin.enrollment.config.FeignClientInterceptorConfig;
import com.sachin.enrollment.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service", configuration = FeignClientInterceptorConfig.class)
public interface UserClientFeign {

    @GetMapping("/api/users/{id}")
    UserDto getUserById(@PathVariable("id") Long userId);

    @PostMapping("/api/users/batch")
    List<UserDto> getUsersByIds(@RequestBody List<Long> ids);
}

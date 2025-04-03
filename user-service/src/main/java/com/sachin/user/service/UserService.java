package com.sachin.user.service;

import com.sachin.user.dto.UserDto;
import com.sachin.user.entity.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long userId, UserDto updatedUser);

    void deleteUser(Long userId);

    UserDto searchByUsernameOrNameOrEmail(String query);
}

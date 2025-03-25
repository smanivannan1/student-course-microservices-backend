package com.sachin.user.mapper;

import com.sachin.user.dto.UserDto;
import com.sachin.user.entity.User;

public class UserMapper {
    public static UserDto maptoUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole());
    }

    public static User maptoUser(UserDto userDto){
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole()
        );
    }
}

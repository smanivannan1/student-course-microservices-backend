package com.sachin.user.service.impl;


import com.sachin.user.dto.UserDto;
import com.sachin.user.entity.User;
import com.sachin.user.exception.ResourceNotFoundException;
import com.sachin.user.mapper.UserMapper;
import com.sachin.user.repository.UserRepository;
import com.sachin.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceimpl implements UserService{

    private UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.maptoUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.maptoUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User does not exist with given ID:"+ userId));
        return UserMapper.maptoUserDto(user);
    }

    @Override
    public List<UserDto> getUsersByIds(List<Long> ids) {
        List<User> users = userRepository.findAllById(ids);
        return users.stream()
                .map(UserMapper::maptoUserDto)  // Use your UserMapper here
                .collect(Collectors.toList());
    }


    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            UserDto userDto = UserMapper.maptoUserDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public UserDto updateUser(Long userId, UserDto updatedUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException
                ("User does not exist with given ID:" + userId));

        user.setName(updatedUser.getName());
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setRole(updatedUser.getRole());

        User updatedUserobj = userRepository.save(user);

        return UserMapper.maptoUserDto(updatedUserobj);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException
                ("User does not exist with given ID:" + userId));

        userRepository.deleteById(userId);
    }

    public UserDto searchByUsernameOrNameOrEmail(String query) {
        User user = userRepository.findByUsernameIgnoreCaseOrNameIgnoreCaseOrEmailIgnoreCase(query, query, query)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserMapper.maptoUserDto(user);
    }

}
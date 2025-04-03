package com.sachin.user.controller;

import com.sachin.user.dto.UserDto;
import com.sachin.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    //Build Add User REST API
    @PreAuthorize("hasRole('STUDENT') or hasRole('INSTRUCTOR')")
    @PostMapping

    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        System.out.println("Received User: " + userDto); // Debugging log
        UserDto savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    //Get User by ID REST API
    @PreAuthorize("hasRole('STUDENT') or hasRole('INSTRUCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id")Long userId){
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }


    //Get all Users
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDtoList = userService.getAllUsers();
        return ResponseEntity.ok(userDtoList);
    }

    //Update a User
    @PreAuthorize("hasRole('STUDENT') or hasRole('INSTRUCTOR')")
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody UserDto updatedUser){
        UserDto userDto = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(userDto);

    }

    //Delete User REST API
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User with ID number " + userId + " has been deleted");

    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/test-student")
    public String testStudentEndpoint() {
        return "Hello student!";
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @GetMapping("/search")
    public ResponseEntity<UserDto> searchUser(@RequestParam String query) {
        UserDto user = userService.searchByUsernameOrNameOrEmail(query.trim());
        return ResponseEntity.ok(user);
    }
}
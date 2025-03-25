package com.sachin.user.controller;

import com.sachin.user.dto.AuthResponseDto;
import com.sachin.user.dto.LoginRequestDto;
import com.sachin.user.dto.RegisterRequestDto;
import com.sachin.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}

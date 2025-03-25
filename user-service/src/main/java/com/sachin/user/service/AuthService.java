package com.sachin.user.service;

import java.util.Map;
import java.util.HashMap;
import com.sachin.user.dto.RegisterRequestDto;
import com.sachin.user.dto.LoginRequestDto;
import com.sachin.user.dto.AuthResponseDto;
import com.sachin.user.entity.User;
import com.sachin.user.repository.UserRepository;
import com.sachin.user.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto register(RegisterRequestDto request) {
        var user = new User(
                null,
                request.getName(),
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole()
        );
        userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        var token = jwtService.generateToken(claims, user);
        return new AuthResponseDto(token);
    }

    public AuthResponseDto authenticate(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        var token = jwtService.generateToken(claims, user);
        return new AuthResponseDto(token);
    }
}

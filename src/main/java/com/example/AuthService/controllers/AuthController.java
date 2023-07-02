package com.example.AuthService.controllers;

import com.example.AuthService.security.service.auth.AuthService;
import com.example.AuthService.dto.auth.LoginRequest;
import com.example.AuthService.dto.auth.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name="Auth-service", description="Authentication")
public class AuthController {
    private final AuthService authService;
    @Operation(summary = "User login used with email and password")
    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Registering a new user")
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        return authService.createCustomer(request);
    }

}

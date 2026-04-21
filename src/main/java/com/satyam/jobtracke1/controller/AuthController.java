package com.satyam.jobtracke1.controller;

import com.satyam.jobtracke1.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Register aur Login APIs")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Naya user register karo")
    public ResponseEntity<String> register(@RequestBody Map<String, String> request) {
        String result = authService.register(
                request.get("username"),
                request.get("password")
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    @Operation(summary = "Login karo aur JWT token lo")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String token = authService.login(
                request.get("username"),
                request.get("password")
        );
        return ResponseEntity.ok(token);
    }
}

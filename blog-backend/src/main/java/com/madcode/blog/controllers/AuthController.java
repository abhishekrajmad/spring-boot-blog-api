package com.madcode.blog.controllers;

import com.madcode.blog.domain.dtos.AuthResponse;
import com.madcode.blog.domain.dtos.LoginRequest;
import com.madcode.blog.domain.dtos.RegisterRequest;
import com.madcode.blog.services.AuthenticationService;
import com.madcode.blog.services.RegisterUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API" ,description = "Prove your identity!!")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final RegisterUserService registerUserService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        UserDetails userDetails = authenticationService.authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        String tokenValue = authenticationService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponse.builder()
                .token(tokenValue)
                .expiresIn(86400)
                .build();
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        registerUserService.register(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }


}

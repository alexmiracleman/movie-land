package com.movieland.web.controller;

import com.movieland.common.LogHandlerInterceptor;
import com.movieland.web.controller.request.AuthRequest;
import com.movieland.web.controller.response.AuthenticationResponse;
import com.movieland.dto.UserRegistrationDto;
import com.movieland.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserRegistrationDto userRegistrationDto
    ) {
        return ResponseEntity.ok(authService.register(userRegistrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthRequest authRequest
    ) {
        log.info("logging in for user {}", authRequest.getEmail());
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

    @DeleteMapping("/logout")
    public void logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        authService.logout(authHeader);
    }

}

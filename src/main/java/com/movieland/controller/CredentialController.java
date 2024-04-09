package com.movieland.controller;

import com.movieland.controller.request.AuthRequest;
import com.movieland.controller.response.AuthResponse;
import com.movieland.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class CredentialController {

    private final SecurityService securityService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = securityService.authenticate(authRequest);
        if (authResponse == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(authResponse);
    }

    @DeleteMapping("/logout")
    public void logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        securityService.logout(token);

    }
}

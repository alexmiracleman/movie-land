package com.movieland.service;

import com.movieland.controller.request.AuthRequest;
import com.movieland.controller.response.AuthResponse;

import java.util.UUID;

public interface SecurityService {

    default String generateToken() {
        return UUID.randomUUID().toString();
    }

    AuthResponse authenticate(AuthRequest authRequest);

    void logout(String token);
}

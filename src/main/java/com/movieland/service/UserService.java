package com.movieland.service;

import com.movieland.entity.User;

public interface UserService {
    User findByEmail(String email);
}

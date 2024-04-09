package com.movieland.service.impl;

import com.movieland.common.Session;
import com.movieland.controller.request.AuthRequest;
import com.movieland.controller.response.AuthResponse;
import com.movieland.entity.User;
import com.movieland.service.SecurityService;
import com.movieland.service.UserService;
import com.movieland.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class DefaultSecurityService implements SecurityService {

    private final UserService userService;
    private final PasswordUtils passwordUtils;

    private final List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());


    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        User user = getUser(authRequest.getEmail(), authRequest.getPassword());
        if (user != null) {
            String token = generateToken();
            LocalDateTime expireDate = LocalDateTime.now().plusHours(2);
            Session session = Session.builder()
                    .token(token)
                    .expireDate(expireDate)
                    .user(user)
                    .build();
            sessionList.add(session);

            return AuthResponse.builder()
                    .uuid(token)
                    .nickname(user.getNickname())
                    .build();
        }
        return null;
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    public Session validateSession() {
        sessionList.removeIf(session -> session.getExpireDate().isBefore(LocalDateTime.now()));
        return null;
    }

    public void logout(String authHeader) {
        sessionList.removeIf(session -> session.getToken().equals(authHeader));
    }

    public User getUser(String email, String password) {
        User user = userService.findByEmail(email);
        if (user != null) {
            if (user.getPassword().equals(passwordUtils.generateHash(password))) {
                return user;
            }
        }
        return null;
    }

}

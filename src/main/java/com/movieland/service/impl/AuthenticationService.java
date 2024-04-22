package com.movieland.service.impl;

import com.movieland.controller.request.AuthRequest;
import com.movieland.controller.response.AuthenticationResponse;
import com.movieland.dto.UserDto;
import com.movieland.entity.Token;
import com.movieland.entity.User;
import com.movieland.repository.TokenRepository;
import com.movieland.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setNickname(userDto.getNickname());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user = repository.save(user);

        String uuid = jwtService.generateToken(user);

        saveUserToken(uuid, user);


        return new AuthenticationResponse(uuid, user.getNickname());
    }

    public AuthenticationResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        User user = repository.findByEmail(authRequest.getEmail()).orElseThrow();
        String uuid = jwtService.generateToken(user);

        revokeAllTokensByUser(user);

        saveUserToken(uuid, user);

        return new AuthenticationResponse(uuid, user.getNickname());
    }

    private void saveUserToken(String uuid, User user) {
        Token token = new Token();
        token.setToken(uuid);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    private void revokeAllTokensByUser(User user) {
        List<Token> validTokensListByUser = tokenRepository.findAllTokensByUser(user.getId());

        if(!validTokensListByUser.isEmpty()) {
            validTokensListByUser.forEach(t -> {
                t.setLoggedOut(true);
            });
        }

        tokenRepository.saveAll(validTokensListByUser);
    }

}

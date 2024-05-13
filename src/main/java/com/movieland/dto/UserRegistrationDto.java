package com.movieland.dto;

import lombok.*;

@Getter
@Builder
public class UserRegistrationDto {

    private String email;

    private String password;

    private String nickname;
}

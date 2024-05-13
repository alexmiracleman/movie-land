package com.movieland.dto;

import lombok.*;

@Getter
@Builder
public class ReviewDto {

    private int id;

    private UserDto user;

    private String text;
}

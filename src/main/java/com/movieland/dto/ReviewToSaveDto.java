package com.movieland.dto;

import lombok.*;

@Getter
@Builder
public class ReviewToSaveDto {

    private int movieId;

    private String text;
}

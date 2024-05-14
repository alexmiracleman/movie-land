package com.movieland.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder

public class GenreDto {

    private final int id;

    private final String name;
}

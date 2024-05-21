package com.movieland.dto;

import lombok.*;

@Getter
@Builder
public class MovieShortDto {

    private int id;

    private String nameRussian;

    private String nameNative;

    private int yearOfRelease;

    private Double rating;

    private Double price;

    private String picturePath;

}
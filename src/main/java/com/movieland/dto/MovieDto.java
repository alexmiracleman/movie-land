package com.movieland.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MovieDto {

    private int id;

    private String nameRussian;

    private String nameNative;

    private int yearOfRelease;

    private Double rating;

    private Double price;

    private String picturePath;
}
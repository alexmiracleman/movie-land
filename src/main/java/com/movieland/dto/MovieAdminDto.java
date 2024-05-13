package com.movieland.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class MovieAdminDto {

    private String nameRussian;

    private String nameNative;

    private int yearOfRelease;

    private String description;

    private Double price;

    private Double rating;

    private String picturePath;

    private List<Integer> countries;

    private List<Integer> genres;

}
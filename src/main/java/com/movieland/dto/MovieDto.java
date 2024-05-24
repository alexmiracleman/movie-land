package com.movieland.dto;

import com.movieland.entity.Country;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class MovieDto {

    private int id;

    private String nameRussian;

    private String nameNative;

    private int yearOfRelease;

    private String description;

    private Double rating;

    private Double price;

    private String picturePath;

    private List<CountryDto> countries;

    private List<GenreDto> genres;

    private List<ReviewDto> reviews;
}
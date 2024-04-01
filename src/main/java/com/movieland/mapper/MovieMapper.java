package com.movieland.mapper;

import com.movieland.dto.MovieDto;
import com.movieland.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieDto toMovieDto(Movie movie) {
        if (movie == null) {
            return null;
        }
        MovieDto.MovieDtoBuilder movieDto = MovieDto.builder();
        movieDto.id(movie.getId());
        movieDto.nameRussian(movie.getNameRussian());
        movieDto.nameNative(movie.getNameNative());
        movieDto.yearOfRelease(movie.getYearOfRelease());
        movieDto.rating(movie.getRating());
        movieDto.price(movie.getPrice());
        movieDto.picturePath(movie.getPicturePath());
        return movieDto.build();
    }

}
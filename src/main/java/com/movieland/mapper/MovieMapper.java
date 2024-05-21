package com.movieland.mapper;

import com.movieland.dto.MovieShortDto;
import com.movieland.dto.MovieDto;
import com.movieland.entity.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class, GenreMapper.class})

public interface MovieMapper {

    List<MovieShortDto> toShortDtoList(List<Movie> movies);

    MovieDto toMovieDto(Movie movie);
}

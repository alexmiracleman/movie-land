package com.movieland.mapper;

import com.movieland.dto.MovieShortDto;
import com.movieland.dto.MovieDto;
import com.movieland.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class, GenreMapper.class})

public interface MovieMapper {

    List<MovieShortDto> toShortDtoList(List<Movie> movies);

    MovieDto toMovieDto(Movie movie);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "countries", ignore = true)
    @Mapping(target = "genres", ignore = true)
    MovieDto toMovieDtoMultiThread(Movie movie);
}

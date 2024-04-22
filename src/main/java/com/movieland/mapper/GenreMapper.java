package com.movieland.mapper;

import com.movieland.dto.GenreDto;
import com.movieland.entity.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreDto toGenreDto(Genre genre) {
        if (genre == null) {
            return null;
        }
        GenreDto.GenreDtoBuilder genreDto = GenreDto.builder();
        genreDto.id(genre.getId());
        genreDto.name(genre.getName());
        return genreDto.build();
    }

}
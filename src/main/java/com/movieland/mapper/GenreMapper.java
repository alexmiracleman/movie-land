package com.movieland.mapper;

import com.movieland.dto.GenreDto;
import com.movieland.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface GenreMapper {
    List<GenreDto> toGenreDto(List<Genre> genre);
}

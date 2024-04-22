package com.movieland.service.impl;

import com.movieland.dto.GenreDto;
import com.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGenreService implements GenreService {

    private final GenresCache genresCache;


    @Override
    public List<GenreDto> findAll() {
        return genresCache.findAll();
    }

}

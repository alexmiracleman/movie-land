package com.movieland.service.impl;

import com.movieland.entity.Genre;
import com.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGenreService implements GenreService{

    private final CachedGenreProxy cachedGenreProxy;

    @Override
    public List<Genre> getAllGenres() {
       return cachedGenreProxy.getAllGenres();
    }
}

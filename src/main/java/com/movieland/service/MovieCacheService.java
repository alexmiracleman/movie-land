package com.movieland.service;

import com.movieland.dto.MovieDto;

public interface MovieCacheService {

    MovieDto getMovieFromCache(int movieId);

    void addMovieToCache(int movieId, MovieDto movieDto);
}

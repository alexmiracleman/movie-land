package com.movieland.service;

import com.movieland.dto.MovieDto;
import com.movieland.entity.Movie;

public interface MovieCacheService {

    MovieDto getMovieFromCache(int movieId);

    void addMovieToCache(int movieId, MovieDto movieDto);
}

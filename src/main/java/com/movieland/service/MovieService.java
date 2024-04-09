package com.movieland.service;

import com.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> findRandomMovies();

    List<Movie> findMoviesByGenre(int genreId, String sortBy, String sortOrder);

    List<Movie> findAllMovies(String sortBy, String sortOrder);

    Movie findMovieById(int movieId, String currency);
}

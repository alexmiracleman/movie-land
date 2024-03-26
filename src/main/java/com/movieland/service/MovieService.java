package com.movieland.service;

import com.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getRandomMovies();

    List<Movie> getMoviesByGenre(int genreId, String rating, String price);

    List<Movie> getAllMovies(String rating, String price);
}

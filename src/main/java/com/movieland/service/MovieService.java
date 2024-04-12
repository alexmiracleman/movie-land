package com.movieland.service;

import com.movieland.controller.validation.Currency;
import com.movieland.controller.validation.SortOrderPrice;
import com.movieland.controller.validation.SortOrderRating;
import com.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> findRandomMovies();

    List<Movie> findMoviesByGenre(int genreId, SortOrderRating rating, SortOrderPrice price);

    List<Movie> findAllMovies(SortOrderRating rating, SortOrderPrice price);

    Movie findMovieById(int movieId, Currency currencyValidation);
}

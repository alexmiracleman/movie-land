package com.movieland.service;

import com.movieland.common.Currency;
import com.movieland.dto.MovieDto;
import com.movieland.dto.MovieModifyDto;
import com.movieland.web.controller.validation.SortOrderPrice;
import com.movieland.web.controller.validation.SortOrderRating;
import com.movieland.entity.Movie;

import java.util.List;

public interface MovieService {


    List<Movie> findRandomMovies();

    List<Movie> findMoviesByGenre(int genreId, SortOrderRating rating, SortOrderPrice price);

    List<Movie> findAllMovies(SortOrderRating rating, SortOrderPrice price);

    MovieDto findMovieById(int movieId, Currency currencyValidation);

    void saveMovie(MovieModifyDto movieModifyDto);

    void editMovie(MovieModifyDto movieModifyDto, int id);

    void deleteMovie(int id);

    Movie getByReferenceId(int movieId);

    MovieDto findInDbAndEnrich(int movieId);

}

package com.movieland.service.impl;

import com.movieland.controller.validation.Currency;
import com.movieland.controller.validation.SortOrderPrice;
import com.movieland.controller.validation.SortOrderRating;
import com.movieland.entity.Movie;
import com.movieland.repository.MovieRepository;
import com.movieland.repository.MovieRepositoryCustom;
import com.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieRepositoryCustom movieRepositoryCustom;
    private final CurrencyConverterService CurrencyConverterService;


    @Override
    public List<Movie> findAllMovies(SortOrderRating rating, SortOrderPrice price) {

        Pair<String,String> validateQuery = validateQuery(rating, price);
        String sortBy = validateQuery.getLeft();
        String sortOrder = validateQuery.getRight();

        return movieRepositoryCustom.findAllCustomSortedMovies(sortBy, sortOrder);
    }

    @Override
    public List<Movie> findMoviesByGenre(int genreId, SortOrderRating rating, SortOrderPrice price) {

        Pair<String,String> validateQuery = validateQuery(rating, price);
        String sortBy = validateQuery.getLeft();
        String sortOrder = validateQuery.getRight();

        return movieRepositoryCustom.findAllByGenreIdCustomSortedMovies(genreId, sortBy, sortOrder);
    }

    @Override
    public Movie findMovieById(int movieId, Currency currency) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            if (currency != null) {
                movie.get().setPrice(CurrencyConverterService.convertFromUah(movie.get().getPrice(), currency));
                return movie.get();
            }
            return movie.get();
        }
        return null;
    }

    @Override
    public List<Movie> findRandomMovies() {
        return movieRepository.findThreeRandomMovies();
    }

    private Pair<String, String> validateQuery(SortOrderRating rating, SortOrderPrice price) {

        String sortBy = "id";
        String sortOrder = "asc";
        if (price == null && rating != null && rating.toString().equals("desc")) {
            sortBy = "rating";
            sortOrder = "desc";
        }
        if (rating == null && price != null && price.toString().equals("asc")) {
            sortBy = "price";
        }
        if (rating == null && price != null && price.toString().equals("desc")) {
            sortBy = "price";
            sortOrder = "desc";
        }
        return Pair.of(sortBy, sortOrder);
    }

}




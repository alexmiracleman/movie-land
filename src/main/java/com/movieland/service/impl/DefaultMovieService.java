package com.movieland.service.impl;

import com.movieland.common.Currency;
import com.movieland.dto.MovieDto;
import com.movieland.dto.MovieModifyDto;
import com.movieland.entity.Movie;
import com.movieland.mapper.MovieMapper;
import com.movieland.repository.MovieRepository;
import com.movieland.service.*;
import com.movieland.web.controller.validation.SortOrderPrice;
import com.movieland.web.controller.validation.SortOrderRating;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.movieland.service.MovieEnrichmentService.EnrichmentType.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMovieService implements MovieService {

    private static final Lock LOCK = new ReentrantLock();


    private final MovieRepository movieRepository;
    private final CurrencyConverterService currencyConverterService;
    private final GenreService genreService;
    private final CountryService countryService;
    private final MovieCacheService movieCacheService;
    private final MovieMapper movieMapper;

    private final ReviewService reviewService;
    private final MovieEnrichmentService movieEnrichmentService;


    @Override
    public List<Movie> findAllMovies(SortOrderRating rating, SortOrderPrice price) {

        Pair<String, String> validateQuery = validateQuery(rating, price);
        String sortBy = validateQuery.getLeft();
        String sortOrder = validateQuery.getRight();

        return movieRepository.findAllCustomSortedMovies(sortBy, sortOrder);
    }

    @Override
    public List<Movie> findMoviesByGenre(int genreId, SortOrderRating rating, SortOrderPrice price) {

        Pair<String, String> validateQuery = validateQuery(rating, price);
        String sortBy = validateQuery.getLeft();
        String sortOrder = validateQuery.getRight();

        return movieRepository.findAllByGenreIdCustomSortedMovies(genreId, sortBy, sortOrder);
    }

    @Override
    public MovieDto findMovieById(int movieId, Currency currency) {

        MovieDto movieDto = movieCacheService.getMovieFromCache(movieId);

        if (movieDto != null) {
            if (currency != null) {
                movieDto = adjustCurrency(movieDto, currency);
                return movieDto;
            }
            return movieDto;
        }

        Movie movie = null;

        LOCK.lock();
        try {
            movie = findByIdInDb(movieId);
        } finally {
            LOCK.unlock();
        }

        if (movie == null) {
            return null;
        }

        movieEnrichmentService.enrich(movie, GENRES, COUNTRIES, REVIEWS);

        movieDto = movieMapper.toMovieDto(movie);

        movieCacheService.addMovieToCache(movieId, movieDto);

        if (currency != null) {
            movieDto = adjustCurrency(movieDto, currency);
        }

        return movieDto;
    }

    public Movie findByIdInDb(int movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        return movieOptional.orElse(null);
    }

    public MovieDto adjustCurrency(MovieDto movieDto, Currency currency) {
        double price = currencyConverterService.convertFromUah(movieDto.getPrice(), currency);
        movieDto.setPrice(price);
        return movieDto;
    }


    @Override
    public void saveMovie(MovieModifyDto movieModifyDto) {

        Movie movie = Movie.builder()
                .nameRussian(movieModifyDto.getNameRussian())
                .nameNative(movieModifyDto.getNameNative())
                .picturePath(movieModifyDto.getPicturePath())
                .yearOfRelease(movieModifyDto.getYearOfRelease())
                .price(movieModifyDto.getPrice())
                .rating(movieModifyDto.getRating())
                .description(movieModifyDto.getDescription())
                .genres(genreService.findAllById(movieModifyDto.getGenres()))
                .countries(countryService.findAllById(movieModifyDto.getCountries()))
                .version(0)
                .build();

        movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void editMovie(MovieModifyDto movieModifyDto, int id) {

        Movie movie = getByReferenceId(id);

        if (movieModifyDto.getNameRussian() != null) {
            movie.setNameRussian(movieModifyDto.getNameRussian());
        }
        if (movieModifyDto.getNameNative() != null) {
            movie.setNameNative(movieModifyDto.getNameNative());
        }
        if (movieModifyDto.getPicturePath() != null) {
            movie.setPicturePath(movieModifyDto.getPicturePath());
        }
        if (movieModifyDto.getYearOfRelease() != 0) {
            movie.setYearOfRelease(movieModifyDto.getYearOfRelease());
        }
        if (movieModifyDto.getRating() != null) {
            movie.setRating(movieModifyDto.getRating());
        }
        if (movieModifyDto.getPrice() != null) {
            movie.setPrice(movieModifyDto.getPrice());
        }
        if (movieModifyDto.getDescription() != null) {
            movie.setDescription(movieModifyDto.getDescription());
        }
        if (movieModifyDto.getGenres() != null) {
            movie.setGenres(genreService.findAllById(movieModifyDto.getGenres()));
        }
        if (movieModifyDto.getCountries() != null) {
            movie.setCountries(countryService.findAllById(movieModifyDto.getCountries()));
        }
        movieRepository.save(movie);
        movieCacheService.addMovieToCache(id, movieMapper.toMovieDto(movie));
    }

    @Override
    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }

    @Override
    public Movie getByReferenceId(int movieId) {
        return movieRepository.getReferenceById(movieId);
    }

    @Override
    public List<Movie> findRandomMovies() {
        return movieRepository.findThreeRandomMovies();
    }

    private Pair<String, String> validateQuery(SortOrderRating rating, SortOrderPrice price) {

        String sortBy = "id";
        String sortOrder = "asc";
        if (price == null && rating != null && rating.toString().equalsIgnoreCase("desc")) {
            sortBy = "rating";
            sortOrder = "desc";
        }
        if (rating == null && price != null && price.toString().equalsIgnoreCase("asc")) {
            sortBy = "price";
        }
        if (rating == null && price != null && price.toString().equalsIgnoreCase("desc")) {
            sortBy = "price";
            sortOrder = "desc";
        }
        return Pair.of(sortBy, sortOrder);
    }

}




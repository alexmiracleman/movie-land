package com.movieland.service.impl;

import com.movieland.entity.Movie;
import com.movieland.repository.GenreRepository;
import com.movieland.repository.MovieRepository;
import com.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;


    @Override
    public List<Movie> getAllMovies(String rating, String price) {
        if (rating == null && price != null) {
            if (price.equals("asc")) {
                return movieRepository.findAllByOrderByPriceAsc();
            }
            if (price.equals("desc")) {
                return movieRepository.findAllByOrderByPriceDesc();
            }
        }
        if (price == null && rating != null) {
            if (rating.equals("desc")) {
                return movieRepository.findAllByOrderByRatingDesc();
            }
        }
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> getRandomMovies() {
        return movieRepository.findThreeRandomMovies();
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId, String rating, String price) {
        List<Movie> movies = genreRepository.findById(genreId).getMovies();
        if (rating == null && price != null) {
            if (price.equals("asc")) {
                return movies.stream().sorted(Comparator.comparing(Movie::getPrice)).toList();
            }
            if (price.equals("desc")) {
                return movies.stream().sorted(Comparator.comparing(Movie::getPrice).reversed()).toList();
            }
        }
        if (price == null && rating != null) {
            if (rating.equals("desc")) {
                return movies.stream().sorted(Comparator.comparing(Movie::getRating).reversed()).toList();
            }
        }
        return movies;
    }

}




package com.movieland.controller;

import com.movieland.entity.Country;
import com.movieland.entity.Genre;
import com.movieland.entity.Movie;
import com.movieland.repository.CountryRepository;
import com.movieland.repository.GenreRepository;
import com.movieland.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Testing {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void test() {
        List<Country> countries = countryRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        Movie movie = movieRepository.findById(1).get();

        movie.setGenres(genres);
        movie.setCountries(countries);
        movieRepository.save(movie);
    }

}

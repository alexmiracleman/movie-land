package com.movieland.service;

import com.movieland.entity.Movie;
import com.movieland.entity.Poster;
import com.movieland.repository.GenreRepository;
import com.movieland.repository.MovieRepository;
import com.movieland.service.impl.DefaultMovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DefaultMovieServiceTest {

    @Autowired
    private DefaultMovieService defaultMovieService;
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void setMovieGenre() {

        Movie movie = movieRepository.findById(1).get();
        movie.setGenres(List.of(genreRepository.findById(10), genreRepository.findById(15)));
        movieRepository.save(movie);


    }

}
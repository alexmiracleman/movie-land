package com.movieland.repository;

import com.movieland.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

//    @Test
//    public void testMoviesParsing() {
//
//        List<Movie> movies = movieRepository.findAllMovies();
//        System.out.println(movies);
//
//    }

}
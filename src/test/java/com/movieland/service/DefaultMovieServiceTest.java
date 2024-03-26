package com.movieland.service;

import com.movieland.dto.MovieDto;
import com.movieland.entity.Movie;
import com.movieland.entity.Poster;
import com.movieland.repository.MovieRepository;
import com.movieland.service.impl.DefaultMovieService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DefaultMovieServiceTest {

    @Autowired
    private DefaultMovieService defaultMovieService;
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testGetMoviesByGenre() {

        List<Movie> movies = defaultMovieService.getMoviesByGenre(15, null, "asc");
        System.out.println(movies);
    }

}
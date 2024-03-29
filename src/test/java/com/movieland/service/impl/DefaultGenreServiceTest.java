package com.movieland.service.impl;

import com.movieland.entity.Genre;
import com.movieland.entity.Movie;
import com.movieland.repository.GenreRepository;
import org.dbunit.Assertion;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class DefaultGenreServiceTest {

    private final List<Movie> movies = new ArrayList<>();


    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private DefaultGenreService defaultGenreService;

    @InjectMocks
    private CachedGenreProxy cachedGenreProxy;

    @Test
    public void testGetAllGenresWithEmptyCache() {

        Movie movie1 = new Movie();
        Movie movie2 = new Movie();

        movies.add(movie1);
        movies.add(movie2);

        Genre genreOne = new Genre(1, "Drama", movies);
        Genre genreTwo = new Genre(2, "Crime", movies);
        Genre genreThree= new Genre(2, "Comedy", movies);

        List<Genre> genres = List.of(genreOne, genreTwo, genreThree);

        when(cachedGenreProxy.getAllGenres()).thenCallRealMethod();

        when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> genresToReturn = defaultGenreService.getAllGenres();

        Assertions.assertEquals(genres, genresToReturn);


    }

}
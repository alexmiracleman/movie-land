package com.movieland.service.impl;

import com.movieland.entity.Genre;
import com.movieland.entity.Movie;
import com.movieland.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class DefaultGenreServiceTest {

    private final List<Movie> movies = new ArrayList<>();

    @Spy
    private List<Genre> genreCache = new ArrayList<>();

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private DefaultGenreService defaultGenreService;

    @Autowired
    @InjectMocks
    private CachedGenreProxy cachedGenreProxy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllGenresWithEmptyCache() {

        Movie movie1 = new Movie();
        Movie movie2 = new Movie();

        movies.add(movie1);
        movies.add(movie2);

        Genre genreOne = new Genre(1, "Drama", movies);
        Genre genreTwo = new Genre(2, "Crime", movies);
        Genre genreThree = new Genre(2, "Comedy", movies);

        List<Genre> genres = List.of(genreOne, genreTwo, genreThree);

        when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> genresToReturn = defaultGenreService.findAllGenres();

        Assertions.assertEquals(genres, genresToReturn);
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllGenresWithFromCache() {

        Movie movie1 = new Movie();
        Movie movie2 = new Movie();

        movies.add(movie1);
        movies.add(movie2);

        Genre genreOne = new Genre(1, "Drama", movies);
        Genre genreTwo = new Genre(2, "Crime", movies);
        Genre genreThree = new Genre(2, "Comedy", movies);

        List<Genre> genres = List.of(genreOne, genreTwo, genreThree);

        genreCache.addAll(genres);

        List<Genre> genresToReturn = defaultGenreService.findAllGenres();

        Assertions.assertEquals(genres, genresToReturn);
        verify(genreRepository, times(1)).findAll();
    }

}
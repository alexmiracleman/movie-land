package com.movieland.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class ParseAndPersistTest {

    final static String POSTER = "src/main/resources/FilesToParse/poster.txt";
    final static String MOVIE = "src/main/resources/FilesToParse/movie.txt";
    final static String GENRE = "src/main/resources/FilesToParse/genre.txt";


    @Autowired
    private ParseAndPersist parseAndPersist;


    @Test
    public void parsePostersAndMoviesAndGenres() {
        parseAndPersist.persistMovieList(POSTER, MOVIE, GENRE);
    }

//    @Test
//    public void testPosterPersist() {
//        parseAndPersist.persistPosterList("C:\\Reader\\poster.txt");
//    }

//    @Test
//    public void testGenrePersist() {
//        parseAndPersist.persistGenreList("C:\\Reader\\genre.txt");
//    }

}
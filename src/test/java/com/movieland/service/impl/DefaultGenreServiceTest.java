package com.movieland.service.impl;

import com.movieland.entity.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DefaultGenreServiceTest {

    @Autowired
    private DefaultGenreService defaultGenreService;

    @Test
    public void testGetAllGenresCache() {

        defaultGenreService.getAllGenres();

        defaultGenreService.getAllGenres();
    }




}
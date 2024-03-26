package com.movieland.controller;

import com.movieland.mapper.MovieMapper;
import com.movieland.service.GenreService;
import com.movieland.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;
    @MockBean
    private MovieMapper movieMapper;

    @MockBean
    private GenreService genreService;

    @Test
    void getAllMovies() throws Exception {

        mockMvc.perform(get("/api/v1/movies")
                .contentType(MediaType.APPLICATION_JSON).content("22"))
                .andExpect(status().isOk());

        System.out.println(content());
    }

    @Test
    void getRandomMovies() {
    }

    @Test
    void getAllGenres() {
    }

    @Test
    void getMoviesByGenre() {
    }

    @Test
    void saveMovie() {
    }
}
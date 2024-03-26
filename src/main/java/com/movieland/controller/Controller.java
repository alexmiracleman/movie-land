package com.movieland.controller;

import com.movieland.dto.MovieDto;
import com.movieland.entity.Genre;
import com.movieland.mapper.MovieMapper;
import com.movieland.service.GenreService;
import com.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class Controller {

    private final MovieService movieService;
    private final MovieMapper movieMapper;
    private final GenreService genreService;


    @GetMapping("/movies")
    public List<MovieDto> getAllMovies(
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) String price) {
        return movieService.getAllMovies(rating, price).stream()
                .map(movieMapper::toMovieDto)
                .toList();
    }

    @GetMapping("/movies/random")
    public List<MovieDto> getRandomMovies() {
        return movieService.getRandomMovies().stream()
                .map(movieMapper::toMovieDto)
                .toList();
    }

    @GetMapping("/genre")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/movies/genre/{genreId}")
    public List<MovieDto> getMoviesByGenre(
            @PathVariable int genreId,
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) String price) {
        return movieService.getMoviesByGenre(genreId, rating, price).stream()
                .map(movieMapper::toMovieDto)
                .toList();
    }

}










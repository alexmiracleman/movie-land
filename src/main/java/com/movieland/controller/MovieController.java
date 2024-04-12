package com.movieland.controller;

import com.movieland.controller.validation.Currency;
import com.movieland.controller.validation.SortOrderPrice;
import com.movieland.controller.validation.SortOrderRating;
import com.movieland.dto.MovieDto;
import com.movieland.entity.Movie;
import com.movieland.mapper.MovieMapper;
import com.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;


    @GetMapping("/movies")
    public List<MovieDto> findAllMovies(
            @RequestParam(required = false) SortOrderRating rating,
            @RequestParam(required = false) SortOrderPrice price)
    {
        return movieService.findAllMovies(rating, price).stream()
                .map(movieMapper::toMovieDto)
                .toList();
    }

    @GetMapping("/movies/genre/{genreId}")
    public List<MovieDto> findMoviesByGenre(
            @PathVariable int genreId,
            @RequestParam(required = false) SortOrderRating rating,
            @RequestParam(required = false) SortOrderPrice price) {
        return movieService.findMoviesByGenre(genreId, rating, price).stream()
                .map(movieMapper::toMovieDto)
                .toList();
    }

    @GetMapping("/movies/random")
    public List<MovieDto> findRandomMovies() {
        return movieService.findRandomMovies().stream()
                .map(movieMapper::toMovieDto)
                .toList();
    }

    @GetMapping("/movies/{movieId}")
    public Movie findMoviesById(
            @PathVariable int movieId,
            @RequestParam(required = false) Currency currency) {
        return movieService.findMovieById(movieId, currency);
    }

}










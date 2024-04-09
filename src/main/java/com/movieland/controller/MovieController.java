package com.movieland.controller;

import com.movieland.controller.validation.CurrencyValidation;
import com.movieland.dto.MovieDto;
import com.movieland.entity.Movie;
import com.movieland.mapper.MovieMapper;
import com.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;;


    @GetMapping("/movies")
    public List<MovieDto> findAllMovies(
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) String price) {
        Pair<String,String> validateQuery = validateQuery(rating, price);
        String sortBy = validateQuery.getLeft();
        String sortOrder = validateQuery.getRight();

        return movieService.findAllMovies(sortBy, sortOrder).stream()
                .map(movieMapper::toMovieDto)
                .toList();
    }

    @GetMapping("/movies/random")
    public List<MovieDto> findRandomMovies() {
        return movieService.findRandomMovies().stream()
                .map(movieMapper::toMovieDto)
                .toList();
    }

    @GetMapping("/movies/genre/{genreId}")
    public List<MovieDto> findMoviesByGenre(
            @PathVariable int genreId,
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) String price) {
        Pair<String,String> validateQuery = validateQuery(rating, price);
        String sortBy = validateQuery.getLeft();
        String sortOrder = validateQuery.getRight();

        return movieService.findMoviesByGenre(genreId, sortBy, sortOrder).stream()
                .map(movieMapper::toMovieDto)
                .toList();
    }

    @GetMapping("/movies/{movieId}")
    public Movie findMoviesById(
            @PathVariable int movieId,
            @RequestParam(required = false) String currency) {
        currency = CurrencyValidation.validate(currency);

        return movieService.findMovieById(movieId, currency);
    }

    private Pair<String, String> validateQuery(String rating, String price) {

        String sortBy = "id";
        String sortOrder = "asc";
        if (price == null && rating != null && rating.equalsIgnoreCase("desc")) {
            sortBy = "rating";
            sortOrder = "desc";
        }
        if (rating == null && price != null && price.equalsIgnoreCase("asc")) {
            sortBy = "price";
        }
        if (rating == null && price != null && price.equalsIgnoreCase("desc")) {
            sortBy = "price";
            sortOrder = "desc";
        }
        return Pair.of(sortBy, sortOrder);
    }

}










package com.movieland.web.controller;

import com.movieland.common.Currency;
import com.movieland.dto.MovieModifyDto;
import com.movieland.dto.MovieDto;
import com.movieland.dto.MovieShortDto;
import com.movieland.mapper.MovieMapper;
import com.movieland.web.controller.validation.SortOrderPrice;
import com.movieland.web.controller.validation.SortOrderRating;
import com.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;


    @GetMapping
    public List<MovieShortDto> findAllMovies(
            @RequestParam(required = false) SortOrderRating rating,
            @RequestParam(required = false) SortOrderPrice price) {
        return movieMapper.toShortDtoList(movieService.findAllMovies(rating, price));
    }

    @GetMapping("/genre/{genreId}")
    public List<MovieShortDto> findMoviesByGenre(
            @PathVariable int genreId,
            @RequestParam(required = false) SortOrderRating rating,
            @RequestParam(required = false) SortOrderPrice price) {
        return movieMapper.toShortDtoList(movieService.findMoviesByGenre(genreId, rating, price));
    }

    @GetMapping("/random")
    public List<MovieShortDto> findRandomMovies() {
        return movieMapper.toShortDtoList(movieService.findRandomMovies());
    }

    @GetMapping("/{movieId}")
    public MovieDto findMoviesById(
            @PathVariable int movieId,
            @RequestParam(required = false) Currency currency) {
        return movieService.findMovieById(movieId, currency);
    }

    @PostMapping
    public void saveMovie(
            @RequestBody MovieModifyDto MovieModifyDto
    ) {
        log.info("Saving movie");
        movieService.saveMovie(MovieModifyDto);
    }

    @PutMapping("/{id}")
    public void editMovie(
            @PathVariable int id,
            @RequestBody MovieModifyDto movieModifyDto
    ) {
        log.info("Editing movie");
        movieService.editMovie(movieModifyDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(
            @PathVariable int id
    ) {
        log.info("Deleting movie");
        movieService.deleteMovie(id);
    }

}










package com.movieland.service.impl;

import com.movieland.dto.CountryDto;
import com.movieland.dto.GenreDto;
import com.movieland.dto.MovieDto;
import com.movieland.dto.ReviewDto;
import com.movieland.mapper.CountryMapper;
import com.movieland.mapper.GenreMapper;
import com.movieland.mapper.ReviewMapper;
import com.movieland.service.CountryService;
import com.movieland.service.GenreService;
import com.movieland.service.MovieEnrichmentService;
import com.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class ParallelMovieEnrichmentService implements MovieEnrichmentService {

    private final static int TIMEOUT = 5;

    private final ExecutorService executorService;
    private final GenreService genreService;
    private final CountryService countryService;
    private final ReviewService reviewService;
    private final GenreMapper genreMapper;
    private final CountryMapper countryMapper;
    private final ReviewMapper reviewMapper;


    @Override
    public void enrich(MovieDto movieDto, EnrichmentType... types) {
        log.info("Enriching movie");
        List<EnrichmentType> list = Arrays.stream(types).toList();

        Runnable enrichMovieWithGenres = () -> {
            List<GenreDto> genres = genreMapper.toGenreDto(genreService.findAllByMovieId(movieDto.getId()));
            movieDto.setGenres(genres);
        };

        Runnable enrichMovieWithCountries = () -> {
            List<CountryDto> countries = countryMapper.toCountryDto(countryService.findAllByMovieId(movieDto.getId()));
            movieDto.setCountries(countries);
        };

        Runnable enrichMovieWithReviews = () -> {
            List<ReviewDto> reviews = reviewMapper.toReviewDto(reviewService.findAllByMovieId(movieDto.getId()));
            movieDto.setReviews(reviews);
        };

        List<Runnable> taskList = new ArrayList<>();

        if (list.contains(EnrichmentType.GENRES)) {
            taskList.add(enrichMovieWithGenres);
        }
        if (list.contains(EnrichmentType.COUNTRIES)) {
            taskList.add(enrichMovieWithCountries);
        }
;        if (list.contains(EnrichmentType.REVIEWS)) {
            taskList.add(enrichMovieWithReviews);
        }

        List<Callable<Object>> callableList = taskList.stream().map(Executors::callable).toList();

        try {
            executorService.invokeAll(callableList, TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

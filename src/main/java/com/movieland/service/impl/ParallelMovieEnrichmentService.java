package com.movieland.service.impl;

import com.movieland.dto.CountryDto;
import com.movieland.dto.GenreDto;
import com.movieland.dto.MovieDto;
import com.movieland.dto.ReviewDto;
import com.movieland.entity.Country;
import com.movieland.entity.Genre;
import com.movieland.entity.Review;
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
            System.out.println("Thread# " + Thread.currentThread().getName());
            List<Genre> genres = genreService.findAllByMovieId(movieDto.getId());
            if (Thread.currentThread().isInterrupted()) {
                log.info("The genres enrichment has been interrupted due to timeout!");
                return;
            }
            movieDto.setGenres(genreMapper.toGenreDto(genres));

        };

        Runnable enrichMovieWithCountries = () -> {
            System.out.println("Thread# " + Thread.currentThread().getName());
            List<Country> countries = countryService.findAllByMovieId(movieDto.getId());
            if (Thread.currentThread().isInterrupted()) {
                log.info("The countries enrichment has been interrupted due to timeout!");
                return;
            }
            movieDto.setCountries(countryMapper.toCountryDto(countries));
        };

        Runnable enrichMovieWithReviews = () -> {
            System.out.println("Thread# " + Thread.currentThread().getName());
            List<Review> reviews = reviewService.findAllByMovieId(movieDto.getId());
            if (Thread.currentThread().isInterrupted()) {
                log.info("The reviews enrichment has been interrupted due to timeout!");
                return;
            }
            movieDto.setReviews(reviewMapper.toReviewDto(reviews));
        };

        List<Runnable> taskList = new ArrayList<>();

        if (list.contains(EnrichmentType.GENRES)) {
            taskList.add(enrichMovieWithGenres);
        }
        if (list.contains(EnrichmentType.COUNTRIES)) {
            taskList.add(enrichMovieWithCountries);
        }
        if (list.contains(EnrichmentType.REVIEWS)) {
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
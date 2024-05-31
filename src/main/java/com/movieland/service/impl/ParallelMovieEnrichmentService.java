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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Service
@Profile("dev")
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

        List<Callable<List<?>>> taskList = getCallableList(movieDto, list);


        List<Future<List<?>>> futureList = null;
        try {
            futureList = executorService.invokeAll(taskList, TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        for (Future<List<?>> future : futureList) {

            try {
                if (future.get() instanceof GenreDto) {
                    movieDto.setGenres((List<GenreDto>) future.get());
                }
                if (future.get() instanceof CountryDto) {
                    movieDto.setCountries((List<CountryDto>) future.get());
                }
                if (future.get() instanceof CountryDto) {
                    movieDto.setReviews((List<ReviewDto>) future.get());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<Callable<List<?>>> getCallableList(MovieDto movieDto, List<EnrichmentType> list) {
        List<Callable<List<?>>> taskList = new ArrayList<>();

        if (list.contains(EnrichmentType.GENRES)) {
            taskList.add(() -> new ArrayList<>(genreMapper.toGenreDto(genreService.findAllByMovieId(movieDto.getId()))));
        }
        if (list.contains(EnrichmentType.COUNTRIES)) {
            taskList.add(() -> new ArrayList<>(countryMapper.toCountryDto(countryService.findAllByMovieId(movieDto.getId()))));
        }
        if (list.contains(EnrichmentType.REVIEWS)) {
            taskList.add(() -> new ArrayList<>(reviewMapper.toReviewDto(reviewService.findAllByMovieId(movieDto.getId()))));
        }
        return taskList;
    }


}

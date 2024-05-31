package com.movieland.service.impl;

import com.movieland.dto.MovieDto;
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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
@Profile("prod")
@RequiredArgsConstructor
public class DefaultMovieEnrichmentService implements MovieEnrichmentService {

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

        if (list.contains(EnrichmentType.GENRES)) {
            movieDto.setGenres(genreMapper.toGenreDto(genreService.findAllByMovieId(movieDto.getId())));
        }
        if (list.contains(EnrichmentType.COUNTRIES)) {
            movieDto.setCountries(countryMapper.toCountryDto(countryService.findAllByMovieId(movieDto.getId())));
        }
        if (list.contains(EnrichmentType.REVIEWS)) {
            movieDto.setReviews(reviewMapper.toReviewDto(reviewService.findAllByMovieId(movieDto.getId())));
        }
    }

}

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
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
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

        if(list.contains(EnrichmentType.GENRES)) {
            enrichGenres(movieDto);
        }
        if(list.contains(EnrichmentType.COUNTRIES)) {
            enrichCountries(movieDto);
        }
        if(list.contains(EnrichmentType.REVIEWS)) {
            enrichReviews(movieDto);
        }
    }

    private void enrichGenres(MovieDto movieDto) {
        Future<List<GenreDto>> genresDto = executorService.submit(() -> genreMapper.toGenreDto(genreService.findAllByMovieId(movieDto.getId())));
        try {
            movieDto.setGenres(genresDto.get(TIMEOUT, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Timeout exception for genres enrichment: ", e);
        }
    }

    private void enrichCountries(MovieDto movieDto) {
        Future<List<CountryDto>> countriesDto = executorService.submit(() -> countryMapper.toCountryDto(countryService.findAllByMovieId(movieDto.getId())));
        try {
            movieDto.setCountries(countriesDto.get(TIMEOUT, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Timeout exception for countries enrichment: ", e);
        }
    }

    private void enrichReviews(MovieDto movieDto) {
        Future<List<ReviewDto>> reviewsDto = executorService.submit(() -> reviewMapper.toReviewDto(reviewService.findAllByMovieId(movieDto.getId())));
        try {
            movieDto.setReviews(reviewsDto.get(TIMEOUT, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Timeout exception for countries enrichment: ", e);
        }
    }

}

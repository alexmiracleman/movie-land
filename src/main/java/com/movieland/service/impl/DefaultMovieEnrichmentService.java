package com.movieland.service.impl;

import com.movieland.entity.Country;
import com.movieland.entity.Genre;
import com.movieland.entity.Movie;
import com.movieland.entity.Review;
import com.movieland.service.CountryService;
import com.movieland.service.GenreService;
import com.movieland.service.MovieEnrichmentService;
import com.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMovieEnrichmentService implements MovieEnrichmentService {

    private final ExecutorService executorService;
    private final GenreService genreService;
    private final CountryService countryService;
    private final ReviewService reviewService;

    @Override
    public void enrich(Movie movie, EnrichmentType... types) {

        Future<List<Genre>> genres = executorService.submit(() -> genreService.findAllByMovieId(movie.getId()));
        Future<List<Country>> countries = executorService.submit(() -> countryService.findAllByMovieId(movie.getId()));
        Future<List<Review>> reviews = executorService.submit(() -> reviewService.findAllByMovieId(movie.getId()));

        int timeout = 5;
        try {
            movie.setGenres(genres.get(timeout, TimeUnit.SECONDS));
            movie.setCountries(countries.get(timeout, TimeUnit.SECONDS));
            movie.setReviews(reviews.get(timeout, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Timeout exception: ", e);
        }
    }

}

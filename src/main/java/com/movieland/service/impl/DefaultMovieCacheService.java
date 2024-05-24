package com.movieland.service.impl;

import com.movieland.dto.MovieDto;
import com.movieland.entity.Movie;
import com.movieland.mapper.MovieMapper;
import com.movieland.repository.MovieRepository;
import com.movieland.service.MovieCacheService;
import com.movieland.service.MovieEnrichmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.movieland.service.MovieEnrichmentService.EnrichmentType.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMovieCacheService implements MovieCacheService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final MovieEnrichmentService movieEnrichmentService;

    private final ConcurrentHashMap<Integer, SoftReference<MovieDto>> listReference = new ConcurrentHashMap<>();

    @Override
    public MovieDto getMovieFromCache(int movieId) {
        if (listReference.containsKey(movieId)) {
            return listReference.get(movieId).get();
        }
        log.info("Enriching cache from db for movieId {}", movieId );
        return findInDbAndEnrich(movieId);
    }

    private MovieDto findInDbAndEnrich(int movieId) {
        Optional<Movie> movieFromDb = movieRepository.findById(movieId);
        if (movieFromDb.isPresent()) {

            MovieDto movieDto = movieMapper.toMovieDtoMultiThread(movieFromDb.get());
                log.info("Enriching movie");
            movieEnrichmentService.enrich(movieDto, GENRES, COUNTRIES, REVIEWS);

            addMovieToCache(movieId, movieDto);

            return movieDto;
        }
        return null;
    }

    @Override
    public void addMovieToCache(int movieId, MovieDto movieDto) {
        listReference.compute(movieId, (key, val)
                -> new SoftReference<>(movieDto));
    }

}

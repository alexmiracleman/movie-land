package com.movieland.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.movieland.dto.MovieDto;
import com.movieland.entity.Movie;
import com.movieland.mapper.MovieMapper;
import com.movieland.repository.MovieRepository;
import com.movieland.service.MovieCacheService;
import com.movieland.service.MovieEnrichmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.movieland.service.MovieEnrichmentService.EnrichmentType.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class ParallelMovieCacheService implements MovieCacheService {

    private final MovieRepository movieRepository;
    private final MovieEnrichmentService movieEnrichmentService;
    private final MovieMapper movieMapper;

    private final LoadingCache<Integer, MovieDto> cache = CacheBuilder
            .newBuilder()
            .softValues()
            .build(new CacheLoader<Integer, MovieDto>() {

                @Override
                public MovieDto load(Integer movieId) throws Exception {
                    log.info("Enriching cache from db for movieId {}", movieId);
                    return findInDbAndEnrich(movieId);
                }
            });

    @Override
    public MovieDto getMovieFromCache(int movieId) {
        try {
            return cache.get(movieId);
        } catch (Exception e) {
            log.error("Movie with id {} was not found: ", movieId, e);
        }
        return null;
    }


    @Override
    public void addMovieToCache(int movieId, MovieDto movieDto) {
        cache.put(movieId, movieDto);
    }

    private MovieDto findInDbAndEnrich(int movieId) {
        Optional<Movie> movieFromDb = movieRepository.findById(movieId);
        if (movieFromDb.isPresent()) {

            MovieDto movieDto = movieMapper.toMovieDtoMultiThread(movieFromDb.get());
            movieEnrichmentService.enrich(movieDto, GENRES, COUNTRIES, REVIEWS);
            return movieDto;
        }
        return null;
    }

}



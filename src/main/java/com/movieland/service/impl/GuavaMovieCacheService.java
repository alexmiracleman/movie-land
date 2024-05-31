package com.movieland.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.movieland.dto.MovieDto;
import com.movieland.service.MovieCacheService;
import com.movieland.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GuavaMovieCacheService implements MovieCacheService {

    MovieService movieService;

    @Autowired
    public GuavaMovieCacheService(@Lazy MovieService movieService) {
        this.movieService = movieService;
    }
    private final LoadingCache<Integer, MovieDto> cache = CacheBuilder
            .newBuilder()
            .softValues()
            .build( new CacheLoader<Integer, MovieDto>() {

                @Override
                public MovieDto load(Integer movieId) throws Exception {
                    log.info("Enriching cache from db for movieId {}", movieId);
                    return movieService.findInDbAndEnrich(movieId);
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

}



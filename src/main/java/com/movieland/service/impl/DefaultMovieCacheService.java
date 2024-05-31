package com.movieland.service.impl;

import com.movieland.dto.MovieDto;
import com.movieland.service.MovieCacheService;
import com.movieland.service.MovieService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
@Primary
public class DefaultMovieCacheService implements MovieCacheService {

    MovieService movieService;

    @Autowired
    public DefaultMovieCacheService(@Lazy MovieService movieService) {
        this.movieService = movieService;
    }

    private final ConcurrentHashMap<Integer, SoftReference<MovieDto>> listReference = new ConcurrentHashMap<>();

    @Override
    public MovieDto getMovieFromCache(int movieId) {

        if (listReference.containsKey(movieId)) {
            return listReference.get(movieId).get();
        }
        log.info("Enriching cache from db for movieId {}", movieId);
        return movieService.findInDbAndEnrich(movieId);
    }

    @Override
    public void addMovieToCache(int movieId, MovieDto movieDto) {
        listReference.compute(movieId, (key, val)
                -> new SoftReference<>(movieDto));
    }

}

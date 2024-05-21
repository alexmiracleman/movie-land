package com.movieland.service.impl;

import com.movieland.dto.MovieDto;
import com.movieland.service.MovieCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;


@Service
@RequiredArgsConstructor
public class DefaultMovieCacheService implements MovieCacheService {

    private final ConcurrentHashMap<Integer, SoftReference<MovieDto>> listReference = new ConcurrentHashMap<>();

    @Override
    public MovieDto getMovieFromCache(int movieId) {

            SoftReference<MovieDto> cachedMovieList = listReference.get(movieId);
            if (cachedMovieList != null) {
                return cachedMovieList.get();
            }
            return null;
    }

    @Override
    public void addMovieToCache(int movieId, MovieDto movieDto) {
        listReference.put(movieId, new SoftReference<>(movieDto));
    }

}

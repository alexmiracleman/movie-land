package com.movieland.service.impl;

import com.movieland.entity.Genre;
import com.movieland.repository.GenreRepository;
import com.movieland.service.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Cache
@RequiredArgsConstructor
public class CachedGenreProxy {

    private final GenreRepository genreRepository;

    private List<Genre> cachedGenre = new ArrayList<>();

    public List<Genre> getAllGenres() {
        if (this.cachedGenre.isEmpty()) {
            getAndCacheAllGenre();
        }
        return new ArrayList<>(cachedGenre);
    }

    @Scheduled(fixedRate = 4, timeUnit = TimeUnit.HOURS)
    public void getAndCacheAllGenre() {
        updateCache();
    }

    private void updateCache() {
        cachedGenre = genreRepository.findAll();
    }


}




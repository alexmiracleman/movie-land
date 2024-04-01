package com.movieland.service.impl;

import com.movieland.entity.Genre;
import com.movieland.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
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
        log.info("Cache has been updated at {}", LocalDateTime.now());
    }

    public void updateCache() {
        cachedGenre = genreRepository.findAll();
    }


}




package com.movieland.service.impl;

import com.movieland.entity.Genre;
import com.movieland.repository.GenreRepository;
import com.movieland.service.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Cache
@Slf4j
@RequiredArgsConstructor
public class CachedGenreProxy {

    private final GenreRepository genreRepository;

    private List<Genre> cachedGenre = new ArrayList<>();

    public List<Genre> findAll() {
        if (this.cachedGenre.isEmpty()) {
            getAndCacheAllGenre();
        }
        return new ArrayList<>(cachedGenre);
    }

    @Scheduled(fixedRate = 4, timeUnit = TimeUnit.HOURS)
    public void getAndCacheAllGenre() {
        invalidate();
        log.info("Cache has been updated at {}", LocalDateTime.now());
    }

    public void invalidate() {
        cachedGenre = genreRepository.findAll();
    }


}




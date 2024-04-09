package com.movieland.service.impl;

import com.movieland.entity.Genre;
import com.movieland.repository.GenreRepository;
import com.movieland.service.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Cache
@RequiredArgsConstructor
public class GenresCache {

    private final GenreRepository genreRepository;

    private List<Genre> cachedGenre = new ArrayList<>();



    //todo ReentrantReadWriteLock
    List<Genre> findAll() {
        if (cachedGenre.isEmpty()) {
            getAndCacheAllGenre();
        }
        return new ArrayList<>(cachedGenre);
    }

    @Scheduled(fixedRate = 4, timeUnit = TimeUnit.HOURS)
    public void getAndCacheAllGenre() {
        invalidate();
    }

    private void invalidate() {
        cachedGenre = Collections.unmodifiableList(genreRepository.findAll());
    }

}




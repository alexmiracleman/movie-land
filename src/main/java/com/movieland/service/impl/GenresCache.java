package com.movieland.service.impl;

import com.movieland.entity.Genre;
import com.movieland.repository.GenreRepository;
import com.movieland.common.annotations.Cache;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Cache
@Slf4j
@RequiredArgsConstructor
public class GenresCache {

    private final GenreRepository genreRepository;
    private List<Genre> cachedGenre = new ArrayList<>();


    List<Genre> findAll() {
        return new ArrayList<>(cachedGenre);
    }

    @PostConstruct
    @Scheduled(fixedRate = 4, timeUnit = TimeUnit.HOURS, initialDelay = 4)
    private void invalidateCache() {
        log.info("Invalidating genre cache");
        cachedGenre = genreRepository.findAll();
    }

}




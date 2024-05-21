package com.movieland.service.impl;

import com.movieland.dto.GenreDto;
import com.movieland.mapper.GenreMapper;
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
    private final GenreMapper genreMapper;
    private volatile List<GenreDto> cachedGenre;


    List<GenreDto> findAll() {
        return new ArrayList<>(cachedGenre);
    }

    @PostConstruct
    @Scheduled(fixedRateString = "${genres.cache.fixed-rate/delay}", timeUnit = TimeUnit.HOURS, initialDelayString = "${genres.cache.fixed-rate/delay}")
    private void invalidateCache() {
        log.info("Invalidating genre cache");
        cachedGenre = genreMapper.toGenreDto(genreRepository.findAll());
    }

}




package com.movieland.service.impl;

import com.movieland.dto.GenreDto;
import com.movieland.entity.Genre;
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
    private List<GenreDto> cachedGenre = new ArrayList<>();
    //todo volatile

    List<GenreDto> findAll() {
        return new ArrayList<>(cachedGenre);
    }

    @PostConstruct
    @Scheduled(fixedRateString = "${genres.cache.fixed-rate}", timeUnit = TimeUnit.HOURS, initialDelayString = "${genres.cache.fixed-delay}")
    private void invalidateCache() {
        log.info("Invalidating genre cache");
        cachedGenre = genreRepository.findAll().stream()
                .map(genreMapper::toGenreDto)
                .toList();
    }

}




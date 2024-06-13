package com.movieland.configuration;

import com.movieland.dto.MovieDto;
import com.movieland.service.MovieService;
import com.movieland.util.SoftReferenceCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.function.Function;

@Configuration
public class CacheConfig {

    @Bean
    public SoftReferenceCache<Integer, MovieDto> movieCache(@Lazy @Autowired MovieService movieService) {
        Function<Integer, MovieDto> movieLoader = movieService::findInDbAndEnrich;
        return new SoftReferenceCache<>(movieLoader);
    }
}
